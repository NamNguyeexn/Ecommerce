package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.ReviewDTO;
import com.prod.facades.IReviewFacade;
import com.prod.models.products.Review;
import com.prod.services.products.IProductService;
import com.prod.services.products.IReviewService;
import com.prod.utils.ConvertListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewFacade implements IReviewFacade {
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private ConvertListPage<Review> convertListPage;
    @Autowired
    private IProductService productService;
    @Override
    public ResponseObject<Review> createReview(ReviewDTO review, int userId) {
        Review newReview = reviewService.createReview(Review.builder()
                .product_id(review.getProduct_id())
                .user_id(userId)
                .value(review.getValue())
                        .content(Objects.equals(review.getContent(), "") ? "" : review.getContent())
                .build());
        productService.updateProductWithReviews(review.getProduct_id(), newReview);
        return ResponseObject.<Review>builder()
                .message("Tao review thanh cong")
                .isSuccess(true)
                .data(newReview)
                .build();
    }

    @Override
    public ResponseObject<Review> getReviewById(int id) {
        Optional<Review> review = reviewService.getReviewById(id);
        if (review.isPresent()) {
            return ResponseObject.<Review>builder()
                    .isSuccess(true)
                    .message("Lay review thanh cong")
                    .data(review.get())
                    .build();
        } else return ResponseObject.<Review>builder()
                .message("Khong the tim review")
                .build();
    }

    @Override
    public ResponseObject<Page<Review>> getAllReviewsByProductId(int id, int page, int size, String sortField, String sortDirect) {
        List<Review> review = reviewService.getPageReviewsByProductId(id, page, size, sortField, sortDirect).getContent();
        if (!review.isEmpty()) {
            return ResponseObject.<Page<Review>>builder()
                    .isSuccess(true)
                    .message("Lay danh sach review thanh cong")
                    .data(convertListPage.listToPage(review, page, size))
                    .build();
        } else return ResponseObject.<Page<Review>>builder()
                .message("Khong the tim danh sach review")
                .build();
    }

    @Override
    public ResponseObject<Page<Review>> getAllReviewsByUserId(int id, int page, int size, String sortField, String sortDirect) {
        List<Review> review = reviewService.getPageReviewsByUserId(id, page, size, sortField, sortDirect).getContent();
        if (!review.isEmpty()) {
            return ResponseObject.<Page<Review>>builder()
                    .isSuccess(true)
                    .message("Lay danh sach review thanh cong")
                    .data(convertListPage.listToPage(review, page, size))
                    .build();
        } else return ResponseObject.<Page<Review>>builder()
                .message("Khong the tim danh sach review")
                .build();    }

    @Override
    public ResponseObject<Page<Review>> getAllReviewsByValueGreaterThan(double value, int page, int size, String sortField, String sortDirect) {
        List<Review> review = reviewService.getPageReviewsByValueGreaterThan(value, page, size, sortField, sortDirect).getContent();
        if (!review.isEmpty()) {
            return ResponseObject.<Page<Review>>builder()
                    .isSuccess(true)
                    .message("Lay danh sach review thanh cong")
                    .data(convertListPage.listToPage(review, page, size))
                    .build();
        } else return ResponseObject.<Page<Review>>builder()
                .message("Khong the tim danh sach review")
                .build();    }

    @Override
    public ResponseObject<Page<Review>> getAllReviewsByValueLessThan(double value, int page, int size, String sortField, String sortDirect) {
        List<Review> review = reviewService.getPageReviewsByValueLessThan(value, page, size, sortField, sortDirect).getContent();
        if (!review.isEmpty()) {
            return ResponseObject.<Page<Review>>builder()
                    .isSuccess(true)
                    .message("Lay danh sach review thanh cong")
                    .data(convertListPage.listToPage(review, page, size))
                    .build();
        } else return ResponseObject.<Page<Review>>builder()
                .message("Khong the tim danh sach review")
                .build();    }

    @Override
    public ResponseObject<Page<Review>> getAllReviewsByValueBetween(double min, double max, int page, int size, String sortField, String sortDirect) {
        List<Review> review = reviewService.getPageReviewsByValueBetween(min, max, page, size, sortField, sortDirect).getContent();
        if (!review.isEmpty()) {
            return ResponseObject.<Page<Review>>builder()
                    .isSuccess(true)
                    .message("Lay danh sach review thanh cong")
                    .data(convertListPage.listToPage(review, page, size))
                    .build();
        } else return ResponseObject.<Page<Review>>builder()
                .message("Khong the tim danh sach review")
                .build();    }
}
