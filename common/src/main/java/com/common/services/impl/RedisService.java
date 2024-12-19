package com.common.services.impl;

import com.common.services.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Primary
public class RedisService implements IRedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveWithTimeLine(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public boolean isKeyALive(String key) {
        return get(key) != null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
