package com.moneycat.budget.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record StatisticsResponse(

        @Schema(description = "지난 달 대비 총액 소비율")
        BigDecimal totalSpendingRateLastMonth,

        @Schema(description = "지난 달 대비 카테고리 별 소비율")
        List<CategorySpendingRateResponse> categorySpendingRate,

        @Schema(description = "지난 요일 대비 소비율")
        BigDecimal weekdaySpendingRate,

        @Schema(description = "다른 유저 대비 소비율")
        BigDecimal userComparisonRate
) {}