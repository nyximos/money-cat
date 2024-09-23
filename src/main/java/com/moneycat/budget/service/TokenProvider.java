package com.moneycat.budget.service;

import com.moneycat.core.exception.InvalidTokenException;
import com.moneycat.core.util.TokenUtils;
import com.moneycat.core.wrapper.TokenUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class TokenProvider {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.prefix}")
    private String tokenPrefix;

    private SecretKey getSecretKey() {
        return TokenUtils.getSecretKey(key);
    }

    public String issueToken(Map<String, Object> tokenInfo, long current, long duration) {
        return tokenPrefix + StringUtils.SPACE +
                Jwts.builder()
                        .subject(String.valueOf(tokenInfo.get("id")))
                        .claims(tokenInfo)
                        .issuedAt(new Date(current))
                        .expiration(new Date(current + duration))
                        .signWith(getSecretKey())
                        .compact();
    }

    private Claims parseJwt(String token) {
        return TokenUtils.extractAllClaims(TokenUtils.getSecretKey(key),token);
    }

    public Long extractId(String token) {
        return Long.valueOf(extractClaim(token, Claims::getSubject));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> (String) claims.get("email"));
    }

    public TokenUser extractTokenUser(String token) {
        return new TokenUser(extractId(token), extractEmail(token));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseJwt(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String removePrefix(String token) {
        if (token.startsWith(tokenPrefix + StringUtils.SPACE)) {
            return token.substring(tokenPrefix.length() + 1);
        }
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = parseJwt(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}