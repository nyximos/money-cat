package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.request.BudgetRequest;
import com.moneycat.budget.controller.model.request.SpendingCreateRequest;
import com.moneycat.budget.service.SpendingService;
import com.moneycat.core.wrapper.ResultResponse;
import com.moneycat.core.wrapper.TokenUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.moneycat.core.constant.MoneyCatConstants.TOKEN_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/spending")
@Tag(name = "지출 API", description = "지출 관련 작업을 처리합니다.")
public class SpendingController {

    private final SpendingService spendingService;

    @PostMapping
    @Operation(summary = "지출 설정", description = "지출을 생성합니다.")
    public ResultResponse<Void> createSpending(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                               @Valid @RequestBody SpendingCreateRequest spendingRequest) {
        spendingService.addSpending(tokenUser.id(), spendingRequest);
        return new ResultResponse<>();
    }
}
