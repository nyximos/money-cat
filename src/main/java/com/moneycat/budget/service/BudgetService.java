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

    private Map<Long, BigDecimal> calculateAverageCategoryPercentage(List<BudgetCategoryPercentageDto> budgets) {
        Map<Long, BigDecimal> totalPercentageByCategory = new HashMap<>();
        Map<Long, Long> userCountByCategory = new HashMap<>();

        Map<Long, BigDecimal> userTotalBudgetMap = budgets.stream()
                .collect(Collectors.groupingBy(
                        BudgetCategoryPercentageDto::getUserId,
                        Collectors.reducing(BigDecimal.ZERO, BudgetCategoryPercentageDto::getAmount, BigDecimal::add)
                ));

        for (BudgetCategoryPercentageDto budget : budgets) {
            Long categoryId = budget.getCategoryId();
            BigDecimal totalAmount = budget.getAmount();
            BigDecimal userTotal = userTotalBudgetMap.get(budget.getUserId());

            BigDecimal categoryPercentage = totalAmount.divide(userTotal, 4, RoundingMode.HALF_UP);

            totalPercentageByCategory.put(categoryId, totalPercentageByCategory.getOrDefault(categoryId, BigDecimal.ZERO).add(categoryPercentage));
            userCountByCategory.put(categoryId, userCountByCategory.getOrDefault(categoryId, 0L) + 1);
        }

        Map<Long, BigDecimal> categoryPercentages = new HashMap<>();

        for (Map.Entry<Long, BigDecimal> entry : totalPercentageByCategory.entrySet()) {
            Long categoryId = entry.getKey();
            BigDecimal totalPercentage = entry.getValue();
            Long userCount = userCountByCategory.get(categoryId);

            BigDecimal averagePercentage = totalPercentage.divide(BigDecimal.valueOf(userCount), 4, RoundingMode.HALF_UP);
            categoryPercentages.put(categoryId, averagePercentage);
        }
        return categoryPercentages;
    }

}
