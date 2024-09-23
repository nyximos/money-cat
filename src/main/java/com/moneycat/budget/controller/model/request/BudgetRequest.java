package com.moneycat.budget.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetRequest(

        @Schema(description = "카테고리 아이디")
        @NotNull(message = "카테고리 아이디는 될 수 없습니다.")
        Long categoryId,

        @Schema(description = "금액")
        @NotNull(message = "금액은 null이 될 수 없습니다.")
        BigDecimal amount,

        @Schema(description = "시작일")
        @NotNull(message = "시작일은 null이 될 수 없습니다.")
        LocalDate startDate,

        @Schema(description = "종료일")
        @NotNull(message = "종료일은 null이 될 수 없습니다.")
        LocalDate endDate

) {
}
