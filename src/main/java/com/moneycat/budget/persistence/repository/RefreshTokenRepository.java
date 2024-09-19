package com.moneycat.budget.persistence.repository;

import com.moneycat.budget.persistence.repository.entity.RefreshTokenEntity;
import com.moneycat.core.config.DefaultRedisRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends DefaultRedisRepository<RefreshTokenEntity, Long> {
}
