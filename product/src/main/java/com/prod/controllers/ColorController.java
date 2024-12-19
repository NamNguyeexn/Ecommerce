package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.facades.DTO.ColorDTO;
import com.prod.facades.IColorFacade;
import com.prod.models.carts.Color;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/color")
public class ColorController extends Controller<Color> {
    @Autowired
    private IColorFacade colorFacade;

    @Override
    public ResponseEntity<ResponseObject<List<Color>>> notOwners() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject<List<Color>>> serverErrors() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<List<Color>>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<Color>> createOrder(@RequestBody ColorDTO colorDTO,
                                                             HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        colorFacade.createColor(colorDTO)
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject<Color>> updateColor(@RequestBody ColorDTO colorDTO,
                                                             HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        colorFacade.updateColor(colorDTO)
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseObject<Color>> getColor(@RequestParam int id, HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        colorFacade.getColorById(id)
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject<List<Color>>> getListColor() {
        try {
            return ResponseEntity.ok().body(
                    colorFacade.getAllColors()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrors();
        }
    }
    @GetMapping("/find")
    public ResponseEntity<ResponseObject<List<Color>>> getListColor(@RequestParam String color) {
        try {
            return ResponseEntity.ok().body(
                    colorFacade.getColorsByValueLike(color)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrors();
        }
    }


}
