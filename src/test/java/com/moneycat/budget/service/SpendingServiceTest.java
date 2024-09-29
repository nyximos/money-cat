package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.response.CategorySpending;
import com.moneycat.budget.controller.model.response.SummaryResponse;
import com.moneycat.budget.persistence.repository.BudgetRepository;
import com.moneycat.budget.persistence.repository.SpendingRepository;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.budget.service.dto.MonthlyBudgetDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpendingServiceTest {
    @Mock
    private SpendingRepository spendingRepository;

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private SpendingService spendingService;


    @Test
    void 오늘_지출_안내_테스트() {
        // Given
        Long userId = 1L;
        LocalDate today = LocalDate.of(2024, 9, 15);

        List<SpendingEntity> mockMonthlySpendings = Arrays.asList(
                SpendingEntity.builder()
                        .categoryId(1L)
                        .amount(new BigDecimal("310000"))
                        .date(today)
                        .isExcluded(false)
                        .build(),
                SpendingEntity.builder()
                        .categoryId(2L)
                        .amount(new BigDecimal("100000"))
                        .date(today)
                        .isExcluded(false)
                        .build(),
                SpendingEntity.builder()
                        .categoryId(3L)
                        .amount(new BigDecimal("5000"))
                        .date(today)
                        .isExcluded(false)
                        .build()
        );
        when(spendingRepository.selectMonthlySpendings(eq(userId), any(LocalDate.class))).thenReturn(mockMonthlySpendings);

        List<MonthlyBudgetDto> mockMonthlyBudgets = Arrays.asList(
                new MonthlyBudgetDto(1L, "식비", new BigDecimal("1000000")),
                new MonthlyBudgetDto(2L, "교통비", new BigDecimal("500000")),
                new MonthlyBudgetDto(3L, "간식비", new BigDecimal("10000"))
        );
        when(budgetRepository.selectMonthlyBudgets(eq(userId), any(LocalDate.class))).thenReturn(mockMonthlyBudgets);

        // When
        SummaryResponse summaryResponse = spendingService.getTodaySummary(userId, today);

        // Then
        assertNotNull(summaryResponse);
        assertEquals(new BigDecimal("415000"), summaryResponse.totalAmount());
        assertEquals(3, summaryResponse.categoryStats().size());

        CategorySpending firstCategory = summaryResponse.categoryStats().get(0);
        assertEquals(1L, firstCategory.categoryId());
        assertEquals("식비", firstCategory.categoryName());
        assertEquals(new BigDecimal("310000"), firstCategory.amountSpent());
        assertEquals(new BigDecimal("500000"), firstCategory.targetAmount());
        assertEquals(new BigDecimal("62"), firstCategory.riskPercentage());

        CategorySpending secondCategory = summaryResponse.categoryStats().get(1);
        assertEquals(2L, secondCategory.categoryId());
        assertEquals("교통비", secondCategory.categoryName());
        assertEquals(new BigDecimal("100000"), secondCategory.amountSpent());
        assertEquals(new BigDecimal("250000"), secondCategory.targetAmount());
        assertEquals(new BigDecimal("40"), secondCategory.riskPercentage());

        CategorySpending thirdCategory = summaryResponse.categoryStats().get(2);
        assertEquals(3L, thirdCategory.categoryId());
        assertEquals("간식비", thirdCategory.categoryName());
        assertEquals(new BigDecimal("5000"), thirdCategory.amountSpent());
        assertEquals(new BigDecimal("100"), thirdCategory.riskPercentage());
        assertNotNull(thirdCategory.targetAmount());
        assertTrue(thirdCategory.targetAmount().compareTo(BigDecimal.ZERO) > 0);


    }

}