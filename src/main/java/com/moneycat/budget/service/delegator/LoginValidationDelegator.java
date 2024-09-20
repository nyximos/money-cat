package com.moneycat.budget.service.delegator;

import com.moneycat.budget.controller.model.request.LoginRequest;
import com.moneycat.budget.service.delegator.validator.LoginValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginValidationDelegator {

    private final List<LoginValidator> validators;

    public void validate(LoginRequest target) {
        validators.stream().forEach(x -> x.validate(target));
    }
}
