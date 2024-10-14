package com.moneycat.budget.persistence.repository.custom;

import com.moneycat.budget.controller.model.request.SpendingSearchRequest;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.budget.service.dto.MonthlyBudgetDto;
import com.moneycat.budget.service.dto.BudgetSpendingDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SpendingRepositoryCustom {
    List<SpendingDetailResponse> selectAllSpending(Long userId, SpendingSearchRequest searchRequest);

    List<SpendingEntity> selectMonthlySpending(Long userId, LocalDate today);

    List<SpendingEntity> selectMonthlySpendingExcludingToday(Long userId, LocalDate today);

    List<SpendingEntity> selectAllSpendingToday(Long id, LocalDate today);

    List<MonthlyBudgetDto> selectSpendingForPeriod(Long userId, LocalDate startDate, LocalDate endDate);

    BigDecimal getTotalSpent(Long userId, LocalDate startDate, LocalDate endDate);

    List<BudgetSpendingDto> selectOtherUsersBudgetSpending(Long excludedUserId, LocalDate today);

    }
