package com.common.controllers;

import com.common.DTO.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ControllerUtil {
    public static <T> ResponseEntity<Object> ok(T data) {
        return ResponseEntity.status((HttpStatus.OK)).body(new ResponseObject<>(true, data,
//                HttpStatus.OK.value(),
                "ok"));
    }

    public static <T> ResponseEntity<Object> notFound(String message) {
        return ResponseEntity.status((HttpStatus.NOT_FOUND))
                .body(new ResponseObject<T>(
                        false,
                        null,
//                        HttpStatus.NOT_FOUND.value(),
                        message != null ? message : "Cannot find resources"));
    }

    public static ResponseEntity<Object> error(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                        false,
                        null,
//                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message != null ? message : "Internal server error"));
    }

    public static ResponseEntity<Object> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseObject<>(
                        false, null,
//                        HttpStatus.UNAUTHORIZED.value(),
                        message != null ? message : "Unauthorized"));
    }

    public static ResponseEntity<Object> invalidated(Object errors,String message) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ResponseObject<>(false, errors,
//                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        message != null ? message : "Validation errors"));
    }
}
