package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.entity.CategoryEntity;
import com.moneycat.core.config.DefaultJpaRepository;

import java.util.List;

public interface CategoryRepository extends DefaultJpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByParentIdOrderByIdAscNameAsc(Long parentId);
    List<CategoryEntity> findByParentIdNotOrderByIdAscNameAsc(Long parentId);

}
