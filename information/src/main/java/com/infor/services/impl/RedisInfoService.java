package com.infor.services.impl;

import com.common.utils.ConvertJson;

import com.infor.DTO.AccountDTO;
import com.infor.models.User;
import com.infor.services.IRedisInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisInfoService implements IRedisInfoService {
    @Autowired
    private com.common.services.impl.RedisService redisService;
    @Autowired
    private ConvertJson convertJson;
    @Override
    public AccountDTO getAccount(int account_id) {
        try {
            String key = "account_" + account_id;
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
    public void pushUserToRedis(User user) {
        try {
            String key = "account_id:" + user.getAccount_id()+":user";
            String value = convertJson.convertToJson(user);
            redisService.save(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateUserToRedis(User user) {
        try {
            String key = "account_id:" + user.getAccount_id()+":user";
            redisService.delete(key);
            String value = convertJson.convertToJson(user);
            redisService.save(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
