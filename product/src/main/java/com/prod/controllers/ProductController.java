package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.facades.IProductFacade;
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
@RequestMapping("/api/product")
public class ProductController extends Controller<ProductDTO> {
    @Autowired
    private IProductFacade productFacade;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO, HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(productFacade.createProduct(productDTO));
            } else {
                return notOwner();
            }
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject<ProductDTO>> updateProduct(@RequestBody ProductDTO productDTO,
                                                                    HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(productFacade.updateProduct(productDTO));
            } else {
                return notOwner();
            }
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject<Page<ProductDTO>>> getAllProducts(@RequestParam(defaultValue = "1") int page,
                                                                           @RequestParam(defaultValue = "10") int limit,
                                                                           @RequestParam(defaultValue = "0") int category,
                                                                           @RequestParam(defaultValue = "0") int season,
                                                                           @RequestParam(defaultValue = "") List<Integer> label,
                                                                           @RequestParam(defaultValue = "id") String sortField,
                                                                           @RequestParam(defaultValue = "incr") String sortDirection) {
        try {
            return ResponseEntity.ok().body(
                    productFacade.getProducts(page, limit, category, season, label, sortField, sortDirection)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/byLabel")
    public ResponseEntity<ResponseObject<Page<ProductDTO>>> getAllProductsByLabel(@RequestParam(defaultValue = "1") int page,
                                                                                  @RequestParam(defaultValue = "10") int limit,
                                                                                  @RequestParam(defaultValue = "id") String sortField,
                                                                                  @RequestParam(defaultValue = "incr") String sortDirection,
                                                                                  @RequestParam int labelId) {
        try {
            return ResponseEntity.ok().body(
                    productFacade.getProductsByLabelId(labelId, page, limit, sortField, sortDirection)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/bySeason")
    public ResponseEntity<ResponseObject<Page<ProductDTO>>> getAllProductsBySeasonId(@RequestParam(defaultValue = "1") int page,
                                                                                     @RequestParam(defaultValue = "10") int limit,
                                                                                     @RequestParam(defaultValue = "id") String sortField,
                                                                                     @RequestParam(defaultValue = "incr") String sortDirection,
                                                                                     @RequestParam int seasonId) {
        try {
            return ResponseEntity.ok().body(
                    productFacade.getProductsBySeasonId(seasonId, page, limit, sortField, sortDirection)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/byCategory")
    public ResponseEntity<ResponseObject<Page<ProductDTO>>> getAllProductsByCategoryId(@RequestParam(defaultValue = "1") int page,
                                                                                       @RequestParam(defaultValue = "10") int limit,
                                                                                       @RequestParam(defaultValue = "id") String sortField,
                                                                                       @RequestParam(defaultValue = "incr") String sortDirection,
                                                                                       @RequestParam(defaultValue = "0") int categoryId) {
        try {
            return ResponseEntity.ok().body(
                    productFacade.getProductsByCategoryId(categoryId, page, limit, sortField, sortDirection)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseObject<ProductDTO>> getProduct(@RequestParam int productId) {
        try {
            return ResponseEntity.ok().body(
                    productFacade.getProductById(productId)
            );
        } catch (Exception e) {
            return serverError(e);
        }
    }
    @GetMapping("/find")
    public ResponseEntity<ResponseObject<Page<ProductDTO>>> findProduct(@RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int limit,
                                                                        @RequestParam(defaultValue = "0") int category,
                                                                        @RequestParam(defaultValue = "0") int season,
                                                                        @RequestParam(defaultValue = "") List<Integer> label,
                                                                        @RequestParam(defaultValue = "id") String sortField,
                                                                        @RequestParam(defaultValue = "incr") String sortDirection,
                                                                        @RequestParam(defaultValue = "null") String key) {
        try {
            return ResponseEntity.ok().body(
                    productFacade.findProduct(key, category, season, label, page, limit, sortField, sortDirection)
            );
        } catch (Exception e) {
            return serverErrorP();
        }
    }

    @Override
    public ResponseEntity<ResponseObject<List<ProductDTO>>> notOwners() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject<List<ProductDTO>>> serverErrors() {
        return null;
    }

    private ResponseEntity<ResponseObject<Page<ProductDTO>>> serverErrorP() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<Page<ProductDTO>>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }
}
