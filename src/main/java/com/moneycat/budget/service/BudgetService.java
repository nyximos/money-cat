package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.request.BudgetRequest;
import com.moneycat.budget.converter.BudgetConverter;
import com.moneycat.budget.persistence.repository.BudgetRepository;
import com.moneycat.budget.persistence.repository.CategoryRepository;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.core.exception.CategoryNotFoundException;
import com.moneycat.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetConverter budgetConverter;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createBudget(BudgetRequest budgetRequest) {
        userRepository.findById(budgetRequest.userId()).orElseThrow(UserNotFoundException::new);
        categoryRepository.findById(budgetRequest.categoryId()).orElseThrow(CategoryNotFoundException::new);
        budgetRepository.save(budgetConverter.convert(budgetRequest));
    }
}
