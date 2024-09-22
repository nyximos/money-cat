package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.request.BudgetRequest;
import com.moneycat.budget.service.BudgetService;
import com.moneycat.core.wrapper.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/budget")
@Tag(name = "예산 API", description = "예산 관련 작업을 처리합니다.")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "예산 설정", description = "예산을 설정합니다.")
    public ResultResponse<Void> createBudget(@Valid @RequestBody BudgetRequest budgetRequest) {
        budgetService.createBudget(budgetRequest);
        return new ResultResponse<>();
    }

}
