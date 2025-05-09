package com.orange.e_shop.user_service.service.auth;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {


    private final RedisTemplate<String, String> redisTemplate;
    private final JwtService jwtService;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    public TokenBlacklistService(RedisTemplate<String, String> redisTemplate, JwtService jwtService) {
        this.redisTemplate = redisTemplate;
        this.jwtService = jwtService;
    }

    public void blacklistToken(String token) {
        Date expiration = jwtService.getExpirationDateFromToken(token);
        long ttl = (expiration.getTime() - System.currentTimeMillis()) / 1000;

        if (ttl > 0) {
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + token,
                    "1",
                    ttl,
                    TimeUnit.SECONDS
            );
        }
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.toString().equals(
                redisTemplate.opsForValue().get(BLACKLIST_PREFIX + token)
        );
    }
}

