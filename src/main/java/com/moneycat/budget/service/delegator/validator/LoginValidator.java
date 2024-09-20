package com.moneycat.budget.service.delegator.validator;

import com.moneycat.budget.controller.model.request.LoginRequest;

public interface LoginValidator {
    void validate(LoginRequest target);
}