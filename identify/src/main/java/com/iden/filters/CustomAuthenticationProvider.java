package com.iden.filters;

import com.iden.models.Account;
import com.iden.services.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAccountService accountService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String name = authentication.getName();
            String password = authentication.getCredentials().toString();
            Optional<Account> account = accountService.getAccountByUsername(name);
            if(account.isEmpty()){
                return null;
            }
            if (passwordEncoder.matches(password, account.get().getPassword())) {
                return new PreAuthenticatedAuthenticationToken(
                        account, password, new ArrayList<>());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}