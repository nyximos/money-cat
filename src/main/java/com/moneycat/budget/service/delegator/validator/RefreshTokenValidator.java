package com.moneycat.budget.service.delegator.validator;

import com.moneycat.budget.persistence.repository.RefreshTokenRepository;
import com.moneycat.core.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenValidator {

    private final RefreshTokenRepository refreshTokenRepository;

    public void validate(String refreshToken, Long userId) {
        refreshTokenRepository.findById(userId)
                .filter(entity -> entity.getRefreshToken().equals(refreshToken))
                .orElseThrow(InvalidRefreshTokenException::new);
    }
}