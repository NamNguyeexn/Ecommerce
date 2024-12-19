package com.iden.controllers;

import com.common.DTO.ResponseObject;
import com.iden.services.IRedisAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@Slf4j
public class BatchController {
    @Autowired
    private IRedisAccountService redisAccountService;
    @GetMapping()
    public ResponseEntity<ResponseObject<String>> saveAllAccounts() {
        ResponseObject<String> responseObject = new ResponseObject<>();
        try {
            return ResponseEntity.ok(ResponseObject.<String>builder()
                            .data(null)
                            .message("Luu thong tin nguoi dung thanh cong")
                            .isSuccess(true)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.<String>builder()
                            .message("Khong the luu thong tin nguoi dung")
                            .build()
            );
        }
    }
}
