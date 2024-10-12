package com.moneycat.budget.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CategoryRecommendationResponse(

        @Schema(description = "카테고리 아이디")
        Long categoryId,

        @Schema(description = "카테고리명")
        String categoryName,

        @Schema(description = "지출 가능 금액")
        BigDecimal amount
) {}