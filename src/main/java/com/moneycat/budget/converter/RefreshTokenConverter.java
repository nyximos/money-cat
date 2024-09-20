package com.moneycat.budget.converter;

import com.moneycat.budget.persistence.repository.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenConverter {

    RefreshTokenEntity convert(Long id, String refreshToken, long expiredAt);

}
