package com.moneycat.budget.persistence.repository.custom;

import com.moneycat.budget.controller.model.request.SpendingSearchRequest;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;

import java.util.List;

public interface SpendingRepositoryCustom {
    List<SpendingDetailResponse> selectAllSpending(Long userId, SpendingSearchRequest searchRequest);
}
