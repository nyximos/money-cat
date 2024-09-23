package com.moneycat.budget.persistence.repository.custom;

import com.moneycat.budget.service.BudgetCategoryPercentageDto;

import java.time.LocalDate;
import java.util.List;

public interface BudgetRepositoryCustom {
    List<BudgetCategoryPercentageDto> findUserBudgetByMonth(LocalDate startDate, LocalDate endDate);
}
