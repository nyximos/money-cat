package com.moneycat.budget.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @Schema(description = "이메일 주소", example = "test@google.com")
        @NotNull(message = "email은 null이 될 수 없습니다.")
        @Email(message = "유효한 이메일 주소를 입력해 주세요.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        String email,

        @Schema(description = "비밀번호", example = "password123")
        @NotNull(message = "password는 null이 될 수 없습니다.")
        @Size(min = 12, message = "비밀번호는 12자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()]).*$", message = "비밀번호는 숫자, 영문자, 특수문자(!@#$%^&*())를 각각 하나 이상 포함해야 합니다.")
        String password
) {
}