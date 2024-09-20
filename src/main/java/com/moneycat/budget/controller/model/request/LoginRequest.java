package com.moneycat.budget.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @Schema(description = "이메일 주소")
        @NotNull(message = "email은 null이 될 수 없습니다.")
        @Email(message = "유효한 이메일 주소를 입력해 주세요.")
        String email,

        @Schema(description = "비밀번호")
        @NotNull(message = "password는 null이 될 수 없습니다.")
        String password

) {
}