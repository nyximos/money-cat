package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.response.TokenResponse;
import com.moneycat.budget.service.TokenService;
import com.moneycat.core.constant.MoneyCatConstants;
import com.moneycat.core.wrapper.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/token")
@Tag(name = "토큰 API", description = "JWT 토큰 관련 작업을 처리합니다.")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "리프레시 토큰으로 새로운 엑세스 토큰을 발급합니다.")
    public ResultResponse<TokenResponse> reIssue(@RequestHeader(MoneyCatConstants.HEADER_AUTHORIZE_TOKEN) String refreshToken) {
        return new ResultResponse<>(tokenService.reIssue(refreshToken));
    }

}
