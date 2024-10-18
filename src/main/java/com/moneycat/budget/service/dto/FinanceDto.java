package com.moneycat.budget.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class FinanceDto {
    private Long categoryId;
    private String categoryName;
    private BigDecimal amount;
}
