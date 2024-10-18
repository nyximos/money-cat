package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.request.BudgetRecommendationRequest;
import com.moneycat.budget.controller.model.request.BudgetRequest;
import com.moneycat.budget.controller.model.response.BudgetRecommendationResponse;
import com.moneycat.budget.converter.BudgetConverter;
import com.moneycat.budget.persistence.repository.BudgetRepository;
import com.moneycat.budget.persistence.repository.CategoryRepository;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.core.exception.CategoryNotFoundException;
import com.moneycat.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetConverter budgetConverter;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createBudget(Long userId, BudgetRequest budgetRequest) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        categoryRepository.findById(budgetRequest.categoryId()).orElseThrow(CategoryNotFoundException::new);
        budgetRepository.save(budgetConverter.convert(userId, budgetRequest));
    }

    @Transactional(readOnly = true)
    public List<BudgetRecommendationResponse> recommendBudget(Long userId, BudgetRecommendationRequest recommendationRequest) {
        LocalDate startDate = recommendationRequest.startDate().withDayOfMonth(1);
        LocalDate endDate = recommendationRequest.startDate().plusMonths(1).withDayOfMonth(1).minusDays(1);

        List<BudgetCategoryPercentageDto> userBudgets = budgetRepository.findOtherUsersBudgetByMonth(userId, startDate, endDate);
        Map<Long, BigDecimal> categoryPercentageMap = calculateAverageCategoryPercentage(userBudgets);

        return categoryPercentageMap.entrySet().stream()
                .map(entry -> {
                    BigDecimal allocatedAmount = recommendationRequest.amount()
                            .multiply(entry.getValue())
                            .setScale(2, RoundingMode.HALF_UP);
                    return new BudgetRecommendationResponse(entry.getKey(), allocatedAmount);
                })
                .collect(Collectors.toList());
    }

    private Map<Long, BigDecimal> calculateAverageCategoryPercentage(List<BudgetCategoryPercentageDto> userBudgets) {
        Map<Long, BigDecimal> totalAmountByCategory = getLongBigDecimalMap(userBudgets);

        BigDecimal grandTotal = totalAmountByCategory.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        // 카테고리별 비율 계산
        Map<Long, BigDecimal> categoryPercentages = new HashMap<>();
        for (Map.Entry<Long, BigDecimal> entry : totalAmountByCategory.entrySet()) {
            Long categoryId = entry.getKey();
            BigDecimal totalAmount = entry.getValue();
            BigDecimal categoryPercentage = totalAmount.divide(grandTotal, 4, RoundingMode.HALF_UP);
            categoryPercentages.put(categoryId, categoryPercentage);
        }

        return categoryPercentages;
    }

    private static Map<Long, BigDecimal> getLongBigDecimalMap(List<BudgetCategoryPercentageDto> userBudgets) {
        Map<Long, BigDecimal> totalAmountByCategory = new HashMap<>();
        Map<Long, Long> userCountByCategory = new HashMap<>();

        // 각 카테고리별 총액/유저 수 계산
        for (BudgetCategoryPercentageDto dto : userBudgets) {
            totalAmountByCategory.put(dto.getCategoryId(), totalAmountByCategory.getOrDefault(dto.getCategoryId(), BigDecimal.ZERO).add(dto.getAmount()));
            userCountByCategory.put(dto.getCategoryId(), userCountByCategory.getOrDefault(dto.getCategoryId(), 0L) + 1);
        }
        return totalAmountByCategory;
    }

}
