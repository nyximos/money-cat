package com.moneycat.budget.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record SpendingResponse (

        @Schema(description = "지출 총액")
        BigDecimal totalAmount,

        @Schema(description = "지출 목록")
        List<SpendingDetailResponse> spendingList,

        @Schema(description = "시작일")
        LocalDate startDate,

        @Schema(description = "종료일")
        LocalDate endDate
) {}
