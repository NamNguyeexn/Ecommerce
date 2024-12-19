package com.common.exceptions;


import com.common.DTO.ResponseObject;
import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(500).body("GOT IOEXCEPTION :" + e.getMessage());
    }
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException (ServletException e) {
        return ResponseEntity.status(500).body("GOT SERVLET EXCEPTION :" + e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.status(500).body("Data type not available :" + e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validPhoneNumberLength (MethodArgumentNotValidException e){
        return ResponseEntity.status(500).body("GOT EXCEPTION : " + e.getFieldError().getDefaultMessage());
    }
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<String> validArrayIndexOutOfBoundsException (ArrayIndexOutOfBoundsException e){
        return ResponseEntity.status(500).body("Data not available " + e.getMessage());
    }
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<String> noResultException (NoResultException e){
        return ResponseEntity.status(500).body("No result found " + e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException (Exception e) {
        return ResponseEntity.status(500).body("GOT GLOBAL EXCEPTION :" + e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<Object> handlingAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(401)
                .body(new ResponseObject<Object>(
                        false,
                        null,
                        "missing authorization"));
    }

}
