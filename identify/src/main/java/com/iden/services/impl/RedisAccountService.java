package com.iden.services.impl;

import com.common.services.IRedisService;
import com.common.utils.ConvertJson;
import com.iden.models.Account;
import com.iden.repositories.IAccountRepository;
import com.iden.services.IRedisAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
public class RedisAccountService implements IRedisAccountService {
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ConvertJson convertJson;
    @Override
    public void saveAccount(Account account) {
        try {
            String key = "account_id:" + account.getId();
            String value = convertJson.convertToJson(account);
            redisService.save(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public Account getAccountFromRedis(int accountId) {
        try {

            String key = "account_id:" + accountId;
            String value = redisService.get(key);
            Account account = new Account();
            if (value != null) {
                account = convertJson.convertFromJson(value, Account.class);
            } else {
                account = accountRepository.findById(accountId).get();
                redisService.save(key, convertJson.convertToJson(account));
            }
            return account;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    @Override
    public Account updateAccountToRedis(Account account ) {
        try {
            String key = "account_id:" + account.getId();
            redisService.delete(key);
            redisService.save(key, convertJson.convertToJson(account));
            return account;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void pushAccountToRedis(Account account) {
        try {
            String key = "account_id:" + account.getId();
            String value = convertJson.convertToJson(account);
            redisService.save(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
