package com.prod.services.caches.impl;

import com.common.utils.ConvertJson;

import com.prod.DTO.AccountDTO;
import com.prod.DTO.UserDTO;
import com.prod.services.caches.IRedisProdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Primary
public class RedisInfoService implements IRedisProdService {
    @Autowired
    private com.common.services.impl.RedisService redisService;
    @Autowired
    private ConvertJson convertJson;
    @Override
    public AccountDTO getAccount(int account_id) {
        try {
            String key = "account_id:" + account_id;
            String value = redisService.get(key);
            if(value == null) {
                log.info("Account {} not found", account_id);
                return null;
            } else {
                log.info("Account {} found", account_id);
                return convertJson.convertFromJson(value, AccountDTO.class);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public UserDTO getUserByAccount(int accountId) {
        try {
            String key = "account_id:" + accountId + ":user";
            String value = redisService.get(key);
            if(value == null) {
                log.info("User with account {} not found", accountId);
                return null;
            } else {
                log.info("User with account {} found", accountId);
                return convertJson.convertFromJson(value, UserDTO.class);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
