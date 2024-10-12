package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.response.CategorySpending;
import com.moneycat.budget.controller.model.response.RecommendationResponse;
import com.moneycat.budget.controller.model.response.SummaryResponse;
import com.moneycat.budget.persistence.repository.BudgetRepository;
import com.moneycat.budget.persistence.repository.SpendingRepository;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.budget.service.dto.MonthlyBudgetDto;
import com.moneycat.core.constant.MoneyCatConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void 오늘_지출_추천_테스트() {
        // Given
        Long userId = 1L;
        LocalDate today = LocalDate.of(2024, 9, 15);
        BigDecimal minimalAmount = BigDecimal.valueOf(1000);

        List<SpendingEntity> mockSpendings = List.of(
                new SpendingEntity(1L, userId, 1L, BigDecimal.valueOf(140000), today.minusDays(1), "떡볶이", false),
                new SpendingEntity(1L, userId, 2L, BigDecimal.valueOf(20000), today.minusDays(1), "아이스크림", false)
        );

        List<SpendingEntity> mockTodaySpendings = List.of(
                new SpendingEntity(1L, userId, 1L, BigDecimal.valueOf(5000), today.minusDays(1), "김밥", false)
        );

        List<MonthlyBudgetDto> mockBudgets = List.of(
                new MonthlyBudgetDto(1L, "식비", BigDecimal.valueOf(300000)),
                new MonthlyBudgetDto(2L, "간식비", BigDecimal.valueOf(20000))
        );

        when(spendingRepository.selectMonthlySpendingsExcludingToday(userId, today)).thenReturn(mockSpendings);
        when(spendingRepository.selectAllSpendingToday(userId, today)).thenReturn(mockTodaySpendings);
        when(budgetRepository.selectMonthlyBudgets(userId, today)).thenReturn(mockBudgets);

        // When
        RecommendationResponse todayRecommendation = spendingService.getTodayRecommendation(userId, today, minimalAmount);

        // Then
        BigDecimal expectedTotalAmount = new BigDecimal("6000");
        assertEquals(expectedTotalAmount, todayRecommendation.totalAmount(), "총 지출 가능 금액이 예상과 일치해야 합니다.");
        assertEquals(2, todayRecommendation.categoryRecommendationResponses().size(), "카테고리 추천 개수는 2개여야 합니다.");
        assertEquals(MoneyCatConstants.UNDER_BUDGET_MESSAGE, todayRecommendation.message(), "예상된 메시지와 일치해야 합니다.");
        assertEquals(minimalAmount, todayRecommendation.categoryRecommendationResponses().get(1).amount(), "간식비 추천 금액이 최소 금액이어야 합니다.");
    }

}