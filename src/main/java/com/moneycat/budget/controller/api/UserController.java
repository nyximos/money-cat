package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.request.SignUpRequest;
import com.moneycat.budget.service.UserService;
import com.moneycat.core.wrapper.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/users")
@Tag(name = "사용자 API", description = "사용자 관련 작업을 처리합니다.")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원가입", description = "회원가입을 합니다..")
    public ResultResponse<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        return new ResultResponse<>();
    }
}
