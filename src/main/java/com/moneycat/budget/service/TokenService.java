package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.response.TokenResponse;
import com.moneycat.budget.converter.RefreshTokenConverter;
import com.moneycat.budget.persistence.repository.RefreshTokenRepository;
import com.moneycat.budget.service.delegator.validator.RefreshTokenValidator;
import com.moneycat.budget.service.delegator.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.access.expire}")
    private long accessExpireDuration;

    @Value("${jwt.refresh.expire}")
    private long refreshExpireDuration;

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenConverter refreshTokenConverter;
    private final TokenValidator tokenValidator;
    private final RefreshTokenValidator refreshTokenValidator;

    public TokenResponse issue(Long id, String email) {
        long current = System.currentTimeMillis();
        Map<String, Object> tokenInfo = createTokenInfo(id, email);
        String accessToken = tokenProvider.issueToken(tokenInfo, current, accessExpireDuration);
        String refreshToken = tokenProvider.issueToken(tokenInfo, current, refreshExpireDuration);
        refreshTokenRepository.save(refreshTokenConverter.convert(id, tokenProvider.removePrefix(refreshToken), current+refreshExpireDuration));
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse reIssue(String refreshToken) {
        refreshToken = tokenProvider.removePrefix(refreshToken);
        String email = tokenProvider.extractEmail(refreshToken);
        Long id = tokenProvider.extractId(refreshToken);

        tokenValidator.validate(refreshToken);
        refreshTokenValidator.validate(refreshToken, id);

        Map<String, Object> tokenInfo = createTokenInfo(id, email);
        long current = System.currentTimeMillis();
        String accessToken = tokenProvider.issueToken(tokenInfo, current, accessExpireDuration);
        String newRefreshToken = tokenProvider.issueToken(tokenInfo, current, refreshExpireDuration);
        refreshTokenRepository.save(refreshTokenConverter.convert(id, tokenProvider.removePrefix(newRefreshToken), current + refreshExpireDuration));
        return new TokenResponse(accessToken, newRefreshToken);
    }

    private Map<String, Object> createTokenInfo(Long id, String email) {
        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("id", id);
        tokenInfo.put("email", email);
        return tokenInfo;
    }
}
