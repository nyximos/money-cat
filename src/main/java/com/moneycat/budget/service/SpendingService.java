package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.request.SpendingRequest;
import com.moneycat.budget.controller.model.request.SpendingSearchRequest;
import com.moneycat.budget.controller.model.response.CategorySpending;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;
import com.moneycat.budget.controller.model.response.SpendingResponse;
import com.moneycat.budget.controller.model.response.SummaryResponse;
import com.moneycat.budget.converter.SpendingConverter;
import com.moneycat.budget.persistence.repository.BudgetRepository;
import com.moneycat.budget.persistence.repository.CategoryRepository;
import com.moneycat.budget.persistence.repository.SpendingRepository;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.budget.persistence.repository.entity.CategoryEntity;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.budget.service.delegator.validator.AccessPermissionValidator;
import com.moneycat.budget.service.dto.MonthlyBudgetDto;
import com.moneycat.core.exception.CategoryNotFoundException;
import com.moneycat.core.exception.SpendingNotFoundException;
import com.moneycat.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpendingService {

    private final SpendingRepository spendingRepository;
    private final SpendingConverter spendingConverter;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AccessPermissionValidator accessPermissionValidator;
    private final BudgetRepository budgetRepository;

    @Transactional
    public void addSpending(Long userId, SpendingRequest spendingRequest) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        categoryRepository.findById(spendingRequest.categoryId()).orElseThrow(CategoryNotFoundException::new);
        spendingRepository.save(spendingConverter.convert(userId, spendingRequest));
    }

    @Transactional
    public void updateSpending(Long spendingId, Long userId, SpendingRequest spendingRequest) {
        SpendingEntity spending = spendingRepository.findById(spendingId).orElseThrow(SpendingNotFoundException::new);
        accessPermissionValidator.validate(spending.getUserId(), userId);
        spending.update(spendingRequest);
    }

    @Transactional
    public void removeSpending(Long userId, Long spendingId) {
        SpendingEntity spending = spendingRepository.findById(spendingId).orElseThrow(SpendingNotFoundException::new);
        accessPermissionValidator.validate(spending.getUserId(), userId);
        spendingRepository.delete(spending);
    }

    @Transactional(readOnly = true)
    public SpendingDetailResponse getSpending(Long userId, Long spendingId) {
        SpendingEntity spending = spendingRepository.findById(spendingId).orElseThrow(SpendingNotFoundException::new);
        accessPermissionValidator.validate(spending.getUserId(), userId);
        CategoryEntity category = categoryRepository.findById(spending.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        return spendingConverter.convert(spending, category.getName());
    }

    @Transactional(readOnly = true)
    public SpendingResponse getAllSpending(Long userId, SpendingSearchRequest searchRequest) {
        List<SpendingDetailResponse> spendingList = spendingRepository.selectAllSpending(userId, searchRequest);
        BigDecimal totalAmount = spendingList.stream()
                .filter(o -> !o.isExcluded())
                .map(SpendingDetailResponse::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new SpendingResponse(totalAmount, spendingList, searchRequest.startDate(), searchRequest.endDate());
    }

    @Transactional(readOnly = true)
    public SummaryResponse getTodaySummary(Long userId, LocalDate today) {
        List<SpendingEntity> monthlySpendings = spendingRepository.selectMonthlySpendings(userId, today);
        List<MonthlyBudgetDto> monthlyBudgets = budgetRepository.selectMonthlyBudgets(userId, today);

        Map<Long, BigDecimal> categorySpendingMap = monthlySpendings.stream()
                .collect(Collectors.groupingBy(
                        SpendingEntity::getCategoryId,
                        Collectors.reducing(BigDecimal.ZERO, SpendingEntity::getAmount, BigDecimal::add)
                ));

        List<CategorySpending> categoryStats = monthlyBudgets.stream()
                .map(budget -> {
                    // 사용 금액
                    BigDecimal amountSpent = categorySpendingMap.getOrDefault(budget.getCategoryId(), BigDecimal.ZERO);

                    // 하루 예산
                    BigDecimal dailyBudget = budget.getAmount().divide(BigDecimal.valueOf(today.lengthOfMonth()), 2, RoundingMode.HALF_UP);

                    // 오늘까지의 적정 예산
                    BigDecimal targetAmount = dailyBudget.multiply(BigDecimal.valueOf(today.getDayOfMonth()));

                    // 위험도 (amountSpent / targetAmount * 100)
                    BigDecimal riskPercentage = amountSpent.divide(targetAmount, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .setScale(0, RoundingMode.HALF_UP);

                    return new CategorySpending(budget.getCategoryId(), budget.getCategoryName(), targetAmount, amountSpent, riskPercentage);
                })
                .collect(Collectors.toList());

        BigDecimal totalAmount = monthlySpendings.stream()
                .map(SpendingEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SummaryResponse(totalAmount, categoryStats);
    }


}
