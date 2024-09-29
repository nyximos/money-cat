package com.moneycat.budget.controller.model.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CategorySpending(
        Long categoryId,
        String categoryName,
        BigDecimal targetAmount,  // 사용 적정 금액
        BigDecimal amountSpent,   // 실제 지출 금액
        BigDecimal riskPercentage // 위험도
) {
    public CategorySpending {
        targetAmount = targetAmount
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}