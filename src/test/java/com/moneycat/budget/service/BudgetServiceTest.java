package com.moneycat.budget.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.moneycat.budget.controller.model.request.BudgetRecommendationRequest;
import com.moneycat.budget.controller.model.response.BudgetRecommendationResponse;
import com.moneycat.budget.persistence.repository.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private BudgetService budgetService;

    private BudgetRecommendationRequest recommendationRequest;

    @BeforeEach
    void setUp() {
        // 총 예산 100만원, 테스트 월 설정
        recommendationRequest = new BudgetRecommendationRequest(
                new BigDecimal("1000000"), LocalDate.of(2024, 9, 1));
    }

    @Test
    void recommendBudget_shouldReturnProperRecommendation() {
        List<BudgetCategoryPercentageDto> mockUserBudgets = Arrays.asList(
                new BudgetCategoryPercentageDto(1L, 1L, new BigDecimal("400000")),
                new BudgetCategoryPercentageDto(1L, 2L, new BigDecimal("300000")),
                new BudgetCategoryPercentageDto(1L, 3L, new BigDecimal("100000")),
                new BudgetCategoryPercentageDto(2L, 1L, new BigDecimal("450000")),
                new BudgetCategoryPercentageDto(2L, 2L, new BigDecimal("350000")),
                new BudgetCategoryPercentageDto(2L, 3L, new BigDecimal("150000"))
        );

        when(budgetRepository.findOtherUsersBudgetByMonth(any(Long.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(mockUserBudgets);

        List<BudgetRecommendationResponse> response = budgetService.recommendBudget(1L, recommendationRequest);

        response.forEach(r -> System.out.println("카테고리 ID: " + r.id() + ", 금액: " + r.amount()));

        assertThat(response).hasSize(3);
        assertThat(response).extracting("id").containsExactlyInAnyOrder(1L, 2L, 3L);
        assertThat(response).extracting("amount")
                .containsExactlyInAnyOrder(
                        new BigDecimal("486900.00"),
                        new BigDecimal("371700.00"),
                        new BigDecimal("141500.00")
                );
    }

}