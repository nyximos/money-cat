package com.moneycat.budget.persistence.repository.custom;

import com.moneycat.budget.service.BudgetCategoryPercentageDto;
import com.moneycat.budget.service.dto.FinanceDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BudgetRepositoryCustom {
    List<BudgetCategoryPercentageDto> findOtherUsersBudgetByMonth(Long excludedUserId, LocalDate startDate, LocalDate endDate);

    List<FinanceDto> selectMonthlyBudgets(Long userId, LocalDate today);

    BigDecimal getBudgets(Long userId, LocalDate today);
}
