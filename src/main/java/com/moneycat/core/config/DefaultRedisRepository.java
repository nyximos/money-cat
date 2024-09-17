package com.moneycat.core.config;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DefaultRedisRepository<T, ID> extends CrudRepository<T, ID> {
}