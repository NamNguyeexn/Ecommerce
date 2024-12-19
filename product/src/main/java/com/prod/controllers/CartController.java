package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.DTO.UserDTO;
import com.prod.facades.DTO.CartDTO;
import com.prod.facades.DTO.CartProductDTO;
import com.prod.facades.ICartFacade;
import com.prod.filters.JwtTokenService;
import com.prod.services.caches.IRedisProdService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartController extends Controller<CartDTO> {
    @Autowired
    private ICartFacade cartFacade;

    @GetMapping("/getCart")
    public ResponseEntity<ResponseObject<Page<CartDTO>>> getCart(@RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 HttpServletRequest request) {
        try {
            UserDTO userDTO = getUser(request);
            AccountDTO accountDTO = getAccount(request);
            if (userDTO == null) {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<Page<CartDTO>>builder()
                                .message(NOT_FOUND_USER)
                                .build()
                );
            } else {
                ResponseObject<Page<CartDTO>> res = cartFacade.getCartByUserId(userDTO.getId(), accountDTO, page, size);
                return ResponseEntity.ok().body(res);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorsP();
        }
    }

    private ResponseEntity<ResponseObject<Page<CartDTO>>> serverErrorsP() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<Page<CartDTO>>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<Page<CartDTO>>> updateCart(@RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestBody List<CartProductDTO> cartProductDTO,
                                                                           HttpServletRequest request) {
        try {
            UserDTO userDTO = getUser(request);
            if (userDTO == null) {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<Page<CartDTO>>builder()
                                .message(NOT_FOUND_USER)
                                .build()
                );
            } else {
                return ResponseEntity.ok().body(cartFacade.updateCartByUserId(cartProductDTO, userDTO.getId(), page, size));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorsP();
        }
    }

    @Override
    public ResponseEntity<ResponseObject<List<CartDTO>>> notOwners() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject<List<CartDTO>>> serverErrors() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<List<CartDTO>>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }

    private ResponseEntity<ResponseObject<List<CartDTO>>> notFoundUser() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<List<CartDTO>>builder()
                        .message(NOT_FOUND_USER)
                        .build()
        );
    }
}
