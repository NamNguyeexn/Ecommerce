package com.iden.services.impl;

import com.iden.models.Account;
import com.iden.models.Role;
import com.iden.repositories.IAccountRepository;
import com.iden.repositories.IRoleRepository;
import com.iden.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.iden.repositories.IAccountRepository.Specs.*;

@Service
@EnableAsync(proxyTargetClass = true)
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public boolean checkIfNameExists(String name) {
       Optional<Role> role= roleRepository.findByName(name);
        return role.isPresent();
    }
//    @Override
//    public Optional<Role> getAccountById(int id) {
//        return accountRepository.findById(id);
//    }
//
//    @Override
//    public Optional<Account> getAccountByUsername(String username) {
//        return accountRepository.findOne(byUsername(username));
//    }
//
//    @Override
//    public Optional<Account> getAccountByEmail(String email) {
//        return accountRepository.findOne(byEmail(email));
//    }
//
//    @Override
//    @Transactional
//    @Async("TransactionalExecutor")
//    public void createAccount(Account account) {
//        accountRepository.save(account);
//    }
//
//    @Override
//    @Transactional
//    @Async("TransactionalExecutor")
//    public void deleteAccount(Account account) {
//        accountRepository.delete(account);
//    }
//
//    @Override
//    public boolean checkIfUsernameExists(String username) {
//        return accountRepository.exists(byUsername(username));
//    }
//
//    @Override
//    public boolean checkIfEmailExists(String email) {
//        return accountRepository.exists(byEmail(email));
//    }
//
//    @Override
//    public boolean checkIfPhoneExists(String phone) {
//        return accountRepository.exists(byPhone(phone));
//    }
}

