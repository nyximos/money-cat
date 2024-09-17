package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.entity.UserEntity;
import com.moneycat.core.config.DefaultJpaRepository;

public interface UserRepository extends DefaultJpaRepository<UserEntity, Long> {
}
