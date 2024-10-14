package com.moneycat.budget.controller.model.response;

import java.math.BigDecimal;

public record CategorySpendingRateResponse (
        Long categoryId,
        String categoryName,
        BigDecimal rate
){}
