package com.moneycat.budget.controller.model.response;

import java.math.BigDecimal;
import java.util.List;

public record SummaryResponse(
        BigDecimal totalAmount,                // 오늘 지출 총액
        List<CategorySpending> categoryStats
) {}