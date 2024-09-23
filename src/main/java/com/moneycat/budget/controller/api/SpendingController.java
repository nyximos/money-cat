package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.request.SpendingRequest;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;
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
                                               @Valid @RequestBody SpendingRequest spendingRequest) {
        spendingService.addSpending(tokenUser.id(), spendingRequest);
        return new ResultResponse<>();
    }

    @PutMapping("/{id}")
    @Operation(summary = "지출 수정", description = "지출 데이터를 수정합니다.")
    public ResultResponse<Void> updateSpending(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                               @PathVariable(value = "id") Long spendingId,
                                               @Valid @RequestBody SpendingRequest spendingRequest) {
        spendingService.updateSpending(spendingId, tokenUser.id(), spendingRequest);
        return new ResultResponse<>();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "지출 삭제", description = "지출 데이터를 삭제합니다.")
    public ResultResponse<Void> removeSpending(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                               @PathVariable(value = "id") Long spendingId) {
        spendingService.removeSpending(tokenUser.id(), spendingId);
        return new ResultResponse<>();
    }

    @GetMapping("/{id}")
    @Operation(summary = "지출 상세 조회", description = "지출 데이터를 조회합니다.")
    public ResultResponse<SpendingDetailResponse> getSpending(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                                              @PathVariable(value = "id") Long spendingId) {
        return new ResultResponse<>(spendingService.getSpending(tokenUser.id(), spendingId));
    }



}
