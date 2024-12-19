package com.iden.services.impl;

import com.iden.models.Account;
import com.iden.repositories.IAccountRepository;
import com.iden.services.IAccountService;
import com.iden.services.IRedisAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static com.iden.repositories.IAccountRepository.Specs.*;

@Service
@EnableAsync(proxyTargetClass = true)
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IRedisAccountService redisAccountService;
    @Override
    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByUsername(String username) {
        return accountRepository.findOne(byUsername(username));
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findOne(byEmail(email));
    }

    @Override
//    @Transactional
//    @Async("TransactionalExecutor")
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    @Async("TransactionalExecutor")
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public boolean checkIfUsernameExists(String username) {
        return accountRepository.exists(byUsername(username));
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        return accountRepository.exists(byEmail(email));
    }

    @Override
    public boolean checkIfPhoneExists(String phone) {
        return accountRepository.exists(byPhone(phone));
    }

    @Override
    public Account changePassword(Account account) {
        return accountRepository.save(account);
    }
}

