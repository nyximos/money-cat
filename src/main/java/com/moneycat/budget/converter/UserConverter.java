package com.moneycat.budget.converter;

import com.moneycat.budget.controller.model.request.SignUpRequest;
import com.moneycat.budget.persistence.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mapping(target = "id", ignore = true)
    UserEntity convert(SignUpRequest signUpRequest);
}
