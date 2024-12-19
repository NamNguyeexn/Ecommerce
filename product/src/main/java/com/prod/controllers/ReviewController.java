package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.UserDTO;
import com.prod.facades.DTO.ReviewDTO;
import com.prod.facades.IReviewFacade;
import com.prod.models.products.Review;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/review")
public class ReviewController extends Controller<Review> {
    @Autowired
    private IReviewFacade reviewFacade;

    @Override
    public ResponseEntity<ResponseObject<List<Review>>> notOwners() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<List<Review>>builder()
                        .message(NOT_OWNER)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseObject<List<Review>>> serverErrors() {
        return null;
    }

    public ResponseEntity<ResponseObject<Page<Review>>> serverErrorP() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<Page<Review>>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<Review>> createReview(@RequestBody ReviewDTO data,
                                                               HttpServletRequest request) {
        try {
            UserDTO userDTO = getUser(request);
            if (userDTO == null) return userNotFound();
            return ResponseEntity.ok().body(
                    reviewFacade.createReview(data, userDTO.getId())
            );
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @PostMapping("/get")
    public ResponseEntity<ResponseObject<Review>> getReviewById(@RequestParam int id) {
        try {
            return ResponseEntity.ok().body(
                    reviewFacade.getReviewById(id)
            );
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @PostMapping("/byProductId")
    public ResponseEntity<ResponseObject<Page<Review>>> getAllReviewsByProductId(@RequestParam(defaultValue = "1") int page,
                                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                                 @RequestParam int data,
                                                                                 @RequestParam(defaultValue = "id") String sortField,
                                                                                 @RequestParam(defaultValue = "incr") String sortDirect,
                                                                                 HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(
                    reviewFacade.getAllReviewsByProductId(data, limit, page, sortField, sortDirect)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @PostMapping("/byUserId")
    public ResponseEntity<ResponseObject<Page<Review>>> getAllReviewsByUserId(@RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "10") int limit,
                                                                              @RequestParam int data,
                                                                              @RequestParam(defaultValue = "id") String sortField,
                                                                              @RequestParam(defaultValue = "incr") String sortDirect,
                                                                              HttpServletRequest request) {
        try {
            UserDTO userDTO = getUser(request);
            if (userDTO == null) return ResponseEntity.badRequest().body(
                    ResponseObject.<Page<Review>>builder()
                            .message(NOT_FOUND_USER)
                            .build()
            );
            return ResponseEntity.ok().body(
                    reviewFacade.getAllReviewsByUserId(data, page, limit, sortField, sortDirect)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/greater")
    public ResponseEntity<ResponseObject<Page<Review>>> getAllReviewsByValueGreaterThan(@RequestParam(defaultValue = "1") int page,
                                                                                        @RequestParam(defaultValue = "10") int limit,
                                                                                        @RequestParam(defaultValue = "id") String sortField,
                                                                                        @RequestParam(defaultValue = "incr") String sortDirect,
                                                                                        @RequestBody double value) {
        try {
            return ResponseEntity.ok().body(
                    reviewFacade.getAllReviewsByValueGreaterThan(value, page, limit, sortField, sortDirect)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/lesser")
    public ResponseEntity<ResponseObject<Page<Review>>> getAllReviewsByValueLesserThan(@RequestParam(defaultValue = "1") int page,
                                                                                       @RequestParam(defaultValue = "10") int limit,
                                                                                       @RequestParam(defaultValue = "id") String sortField,
                                                                                       @RequestParam(defaultValue = "incr") String sortDirect,
                                                                                       @RequestBody double value) {
        try {
            return ResponseEntity.ok().body(
                    reviewFacade.getAllReviewsByValueLessThan(value, page, limit, sortField, sortDirect)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/between")
    public ResponseEntity<ResponseObject<Page<Review>>> getAllReviewsByValueBetween(@RequestParam(defaultValue = "1") int page,
                                                                                    @RequestParam(defaultValue = "10") int limit,
                                                                                    @RequestParam(defaultValue = "id") String sortField,
                                                                                    @RequestParam(defaultValue = "incr") String sortDirect,
                                                                                    @RequestParam double min,
                                                                                    @RequestParam double max) {
        try {
            return ResponseEntity.ok().body(
                    reviewFacade.getAllReviewsByValueBetween(min, max, page, limit, sortField, sortDirect)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }
}
