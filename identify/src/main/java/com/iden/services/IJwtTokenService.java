package com.iden.services;

import com.iden.models.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface IJwtTokenService {
    String generateToken(Account account);
    Integer getAccountId(String token);
    String getUsernameByRequest(HttpServletRequest request);
    boolean validateToken(String token);
    String getTokenFromRequest(HttpServletRequest request);
}
