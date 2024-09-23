package com.moneycat.budget.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetCategoryPercentageDto {

    private Long userId;
    private Long categoryId;
    private BigDecimal amount;

}
