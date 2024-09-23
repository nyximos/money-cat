package com.moneycat.budget.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetRecommendationRequest(

    @Schema(description = "총 예산")
    @NotNull(message = "총 예산은 null이 될 수 없습니다.")
    BigDecimal amount,

    @Schema(description = "시작일")
    @NotNull(message = "시작일 null이 될 수 없습니다.")
    LocalDate startDate

) {}
