package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.request.SpendingRequest;
import com.moneycat.budget.controller.model.request.SpendingSearchRequest;
import com.moneycat.budget.controller.model.response.RecommendationResponse;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;
import com.moneycat.budget.controller.model.response.SpendingResponse;
import com.moneycat.budget.controller.model.response.SummaryResponse;
import com.moneycat.budget.service.SpendingService;
import com.moneycat.core.wrapper.ResultResponse;
import com.moneycat.core.wrapper.TokenUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @GetMapping
    @Operation(summary = "지출 목록 조회", description = "지출 목록을 조회합니다.")
    public ResultResponse<SpendingResponse> getAllSpending(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                                           @Valid @ModelAttribute SpendingSearchRequest searchRequest) {
        return new ResultResponse<>(spendingService.getAllSpending(tokenUser.id(), searchRequest));
    }

    @GetMapping("/summary/today")
    @Operation(summary = "오늘 지출 안내", description = "오늘 지출한 내용을 총액과 카테고리 별 금액으로 알려줍니다.")
    public ResultResponse<SummaryResponse> getTodaySummary(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser) {
    return new ResultResponse<>(spendingService.getTodaySummary(tokenUser.id(), LocalDate.now()));
    }

    @GetMapping("/")
    @Operation(summary = "오늘 지출 추천", description = "설정한 월별 예산을 만족하기 위해 오늘 지출 가능한 금액을 총액과 카테고리 별 금액으로 제공합니다")
    public ResultResponse<RecommendationResponse> getTodayRecommendation(@RequestAttribute(value = TOKEN_USER) TokenUser tokenUser,
                                                                         @RequestParam BigDecimal minimalAmount) {
        return new ResultResponse<>(spendingService.getTodayRecommendation(tokenUser.id(), LocalDate.now(), minimalAmount));
    }


}
