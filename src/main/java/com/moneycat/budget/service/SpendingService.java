package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.request.SpendingRequest;
import com.moneycat.budget.converter.SpendingConverter;
import com.moneycat.budget.persistence.repository.CategoryRepository;
import com.moneycat.budget.persistence.repository.SpendingRepository;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.budget.service.delegator.validator.AccessPermissionValidator;
import com.moneycat.core.exception.CategoryNotFoundException;
import com.moneycat.core.exception.SpendingNotFoundException;
import com.moneycat.core.exception.UnauthorizedAccessException;
import com.moneycat.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpendingService {

    private final SpendingRepository spendingRepository;
    private final SpendingConverter spendingConverter;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AccessPermissionValidator accessPermissionValidator;

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
}
