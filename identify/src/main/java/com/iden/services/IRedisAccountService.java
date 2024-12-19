package com.iden.services;

import com.iden.models.Account;
import org.springframework.stereotype.Service;

@Service
public interface IRedisAccountService {
    void saveAccount(Account account);
    void pushAccountToRedis(Account account);
    Account getAccountFromRedis(int accountId);
    Account updateAccountToRedis(Account account );
}
