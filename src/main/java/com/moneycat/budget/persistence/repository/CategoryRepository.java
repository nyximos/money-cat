package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.entity.CategoryEntity;
import com.moneycat.core.config.DefaultJpaRepository;

public interface CategoryRepository extends DefaultJpaRepository<CategoryEntity, Long> {
}
