package com.moneycat.budget.controller.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategoryResponse(

        @Schema(description = "카테고리 아이디")
        Long id,

        @Schema(description = "카테고리 명")
        String name,

        @Schema(description = "하위 카테고리")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<CategoryResponse> subCategories

        ) {
}
