package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.request.SpendingCreateRequest;
import com.moneycat.budget.converter.SpendingConverter;
import com.moneycat.budget.persistence.repository.CategoryRepository;
import com.moneycat.budget.persistence.repository.SpendingRepository;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.core.exception.CategoryNotFoundException;
import com.moneycat.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpendingService {

    private final SpendingRepository spendingRepository;
    private final SpendingConverter spendingConverter;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public void addSpending(Long userId, SpendingCreateRequest spendingRequest) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        categoryRepository.findById(spendingRequest.categoryId()).orElseThrow(CategoryNotFoundException::new);
        spendingRepository.save(spendingConverter.convert(userId, spendingRequest));
    }
}
