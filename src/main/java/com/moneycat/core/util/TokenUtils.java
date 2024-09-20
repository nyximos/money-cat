package com.moneycat.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class TokenUtils {

    public static SecretKey getSecretKey(String key){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public static Claims extractAllClaims(SecretKey key, String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
