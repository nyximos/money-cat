package com.moneycat.budget.persistence.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenEntity {

    @Id
    private Long id;

    private String refreshToken;

    @TimeToLive
    private long expiredAt;

}