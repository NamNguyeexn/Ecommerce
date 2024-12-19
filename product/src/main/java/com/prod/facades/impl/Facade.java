package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import org.springframework.stereotype.Component;

@Component
public abstract class Facade<T> {
    protected static final String SERVER_ERROR = "Gap loi server";
    public ResponseObject<T> serverError(){
        return ResponseObject.<T>builder()
                .message(SERVER_ERROR)
                .build();
    }
}
