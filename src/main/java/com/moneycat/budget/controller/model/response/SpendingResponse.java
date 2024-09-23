package com.moneycat.budget.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SpendingResponse (
        @Schema(description = "아이디")
        Long id,

        @Schema(description = "카테고리 아이디")
        Long categoryId,

        @Schema(description = "카테고리명")
        String categoryName,

        @Schema(description = "날짜")
        LocalDate date,

        @Schema(description = "금액")
        BigDecimal amount,

        @Schema(description = "제외 여부")
        boolean isExcluded,

        @Schema(description = "메모")
        String memo,

        @Schema(description = "작성일")
        LocalDateTime createdAt,

        @Schema(description = "수정일")
        LocalDateTime updatedAt
) {

}
