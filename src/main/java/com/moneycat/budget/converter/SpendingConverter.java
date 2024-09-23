package com.moneycat.budget.converter;

import com.moneycat.budget.controller.model.request.SpendingRequest;
import com.moneycat.budget.controller.model.response.SpendingResponse;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpendingConverter {

    SpendingEntity convert(Long userId, SpendingRequest source);

    SpendingResponse convert(SpendingEntity spending, String categoryName);
}