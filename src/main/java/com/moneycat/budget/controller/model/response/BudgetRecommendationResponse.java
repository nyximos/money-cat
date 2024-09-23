package com.moneycat.budget.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record BudgetRecommendationResponse (

        @Schema(description = "카테고리 아이디")
        Long id,

        @Schema(description = "금액 아이디")
        BigDecimal amount

) {}
