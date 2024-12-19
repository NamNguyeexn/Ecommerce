package com.iden.services;

import com.iden.models.Account;

import java.util.Optional;

public interface IAccountService {
    //get account
    Optional<Account> getAccountById(int id);
    Optional<Account> getAccountByUsername(String username);
    Optional<Account> getAccountByEmail(String email);
    //create delete
    Account createAccount(Account account);
    void deleteAccount(Account account);
    //validate
    boolean checkIfUsernameExists(String username);
    boolean checkIfEmailExists(String email);
    boolean checkIfPhoneExists(String phone);
    Account changePassword(Account account);
}
