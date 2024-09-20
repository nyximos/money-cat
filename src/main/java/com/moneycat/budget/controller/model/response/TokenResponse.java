package com.moneycat.budget.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(

        @Schema(description = "엑세스 토큰")
        String accessToken,

        @Schema(description = "리프레시 토큰")
        String refreshToken

) {
}