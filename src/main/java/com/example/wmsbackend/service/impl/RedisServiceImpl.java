package com.example.wmsbackend.service.impl;

import com.example.wmsbackend.service.RedisService;
import com.example.wmsbackend.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtils jwtUtils;
    @org.springframework.beans.factory.annotation.Value("${jwt.expire}")
    private Long expiration;

    @Override
    public void storeToken(String userName) {
        String token = jwtUtils.generateToken(userName);

        redisTemplate.opsForValue().set(userName, token, expiration, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getToken(String userName) {
        return redisTemplate.opsForValue().get(userName);
    }

    @Override
    public void deleteToken(String userName) {
        redisTemplate.delete(userName);
    }
}
