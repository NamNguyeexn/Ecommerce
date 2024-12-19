package com.prod.controllers;

import com.common.DTO.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
public interface IController<T, X> {
    ResponseEntity<ResponseObject<T>> createObject(@RequestBody X data, HttpServletRequest request);
    ResponseEntity<ResponseObject<T>> updateObject(@RequestBody X data, HttpServletRequest request);
    ResponseEntity<ResponseObject<Boolean>> deleteObject(@RequestBody X data, HttpServletRequest request);
    ResponseEntity<ResponseObject<T>> getObjectById(@RequestBody X data, HttpServletRequest request);
    ResponseEntity<ResponseObject<List<T>>> getAllObjects();
    ResponseEntity<ResponseObject<List<T>>> getAllObjectsByName(@RequestBody String name, HttpServletRequest request);
}
