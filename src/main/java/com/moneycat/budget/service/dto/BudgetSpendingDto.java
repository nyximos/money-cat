package com.moneycat.budget.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSpendingDto {
    private Long userId;
    private BigDecimal budget;
    private BigDecimal spending;
}
