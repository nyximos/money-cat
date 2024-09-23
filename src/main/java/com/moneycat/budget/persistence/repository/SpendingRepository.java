package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.custom.SpendingRepositoryCustom;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.core.config.DefaultJpaRepository;

public interface SpendingRepository extends DefaultJpaRepository<SpendingEntity, Long>, SpendingRepositoryCustom {
}
