package com.moneycat.budget.converter;

import com.moneycat.budget.controller.model.request.BudgetRequest;
import com.moneycat.budget.persistence.repository.entity.BudgetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BudgetConverter {

    @Mapping(target = "id", ignore = true)
    BudgetEntity convert(BudgetRequest budgetRequest);
}
