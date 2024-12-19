package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.facades.DTO.SizeDTO;
import com.prod.facades.ISizeFacade;
import com.prod.models.carts.Size;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/size")
public class SizeController extends Controller<Size> {
    @Autowired
    private ISizeFacade sizeFacade;

    @Override
    public ResponseEntity<ResponseObject<List<Size>>> notOwners() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject<List<Size>>> serverErrors() {
        return ResponseEntity.ok().body(
                ResponseObject.<List<Size>>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<Size>> createSize(@RequestBody SizeDTO sizeDTO,
                                                           HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        sizeFacade.createSize(sizeDTO)
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject<Size>> updateSize(@RequestBody SizeDTO sizeDTO,
                                                           HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        sizeFacade.updateSize(sizeDTO)
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseObject<Size>> getSizeById(@RequestParam int id,
                                                            HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        sizeFacade.getSizeById(id)
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject<List<Size>>> getAllSize() {
        try {
            return ResponseEntity.ok().body(
                    sizeFacade.getAllSizes()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrors();
        }
    }

}
