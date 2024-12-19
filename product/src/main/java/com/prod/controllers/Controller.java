package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.DTO.UserDTO;
import com.prod.filters.JwtTokenService;
import com.prod.services.caches.IRedisProdService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public abstract class Controller<T> {
    @Autowired
    private IRedisProdService redisProdService;
    @Autowired
    private JwtTokenService jwtTokenService;
    protected static final String SERVER_ERROR = "Gap loi server";
    protected static final String NOT_OWNER = "Nguoi dung khong phai chu cua hang";
    protected static final String NOT_FOUND_USER = "Khong tim thay thong tin nguoi dung";
    protected static final String NOT_FOUND_ACCOUNT = "Khong tim thay tai khoan";

    public ResponseEntity<ResponseObject<T>> serverError(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(
                ResponseObject.<T>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }

    public AccountDTO getAccount(HttpServletRequest request) {
        int account_id = getAccountIdFromTokenRequest(request);
        return redisProdService.getAccount(account_id);
    }

    public UserDTO getUser(HttpServletRequest request) {
        int account_id = getAccountIdFromTokenRequest(request);
        return redisProdService.getUserByAccount(account_id);
    }
    public int getAccountIdFromTokenRequest(HttpServletRequest request) {
        String JWT = jwtTokenService.getTokenFromRequest(request);
        return jwtTokenService.getAccountId(JWT);
    }

    public ResponseEntity<ResponseObject<T>> notOwner() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<T>builder()
                        .message(NOT_OWNER)
                        .build()
        );
    }
    public ResponseEntity<ResponseObject<T>> accountNotFound(){
        return ResponseEntity.badRequest().body(
                ResponseObject.<T>builder()
                        .message(NOT_FOUND_ACCOUNT)
                        .build()
        );
    }

    public ResponseEntity<ResponseObject<T>> userNotFound(){
        return ResponseEntity.badRequest().body(
                ResponseObject.<T>builder()
                        .message(NOT_FOUND_USER)
                        .build()
        );
    }
    abstract public ResponseEntity<ResponseObject<List<T>>> notOwners();
    abstract public ResponseEntity<ResponseObject<List<T>>> serverErrors();
}
