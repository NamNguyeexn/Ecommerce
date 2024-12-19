package com.common.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface IRedisService {
    String get(String key);
    void save(String key, String value);
    void saveWithTimeLine(String key, String value, long time);
    boolean isKeyALive(String key);
    void delete(String key);
}
