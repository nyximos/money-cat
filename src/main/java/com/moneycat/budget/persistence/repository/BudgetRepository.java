package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.entity.BudgetEntity;
import com.moneycat.core.config.DefaultJpaRepository;

public interface BudgetRepository extends DefaultJpaRepository<BudgetEntity, Long> {
}
