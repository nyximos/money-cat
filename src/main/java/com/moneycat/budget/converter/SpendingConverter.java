package com.moneycat.budget.converter;

import com.moneycat.budget.controller.model.request.SpendingCreateRequest;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpendingConverter {

    SpendingEntity convert(Long userId, SpendingCreateRequest source);
}
