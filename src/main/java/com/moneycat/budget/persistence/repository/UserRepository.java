package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.entity.UserEntity;
import com.moneycat.core.config.DefaultJpaRepository;

import java.util.Optional;

public interface UserRepository extends DefaultJpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
