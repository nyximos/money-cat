package com.moneycat.budget.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record RecommendationResponse(
        @Schema(description = "지출 가능 금액")
        BigDecimal totalAmount,

        @Schema(description = "카테고리 별 금액")
        List<CategoryRecommendationResponse> categoryRecommendationResponses,

        @Schema(description = "메시지")
        String message
) {}