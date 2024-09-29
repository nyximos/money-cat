package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.custom.SpendingRepositoryCustom;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.core.config.DefaultJpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpendingRepository extends DefaultJpaRepository<SpendingEntity, Long>, SpendingRepositoryCustom {
    List<SpendingEntity> findByUserIdAndDate(Long userId, LocalDate today);
}
