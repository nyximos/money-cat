package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.request.BudgetRecommendationRequest;
import com.moneycat.budget.controller.model.request.BudgetRequest;
import com.moneycat.budget.controller.model.response.BudgetRecommendationResponse;
import com.moneycat.budget.service.BudgetService;
import com.moneycat.core.wrapper.ResultResponse;
import com.moneycat.core.wrapper.TokenUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moneycat.core.constant.MoneyCatConstants.TOKEN_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/budget")
@Tag(name = "예산 API", description = "예산 관련 작업을 처리합니다.")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "예산 설정", description = "예산을 설정합니다.")
    public ResultResponse<Void> createBudget(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                             @Valid @RequestBody BudgetRequest budgetRequest) {
        budgetService.createBudget(tokenUser.id(), budgetRequest);
        return new ResultResponse<>();
    }

    @PostMapping("/recommend")
    @Operation(summary = "예산 추천", description = "예산을 추천합니다.")
    public ResultResponse<List<BudgetRecommendationResponse>> recommendBudget(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                                                              @RequestBody BudgetRecommendationRequest recommendationRequest) {
        return new ResultResponse<>(budgetService.recommendBudget(tokenUser.id(), recommendationRequest));
    }

}
