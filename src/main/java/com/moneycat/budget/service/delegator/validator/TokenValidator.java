package com.moneycat.budget.service.delegator.validator;

import com.moneycat.budget.service.TokenProvider;
import com.moneycat.core.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final TokenProvider tokenProvider;

    public void validate(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }
}