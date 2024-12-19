package com.prod.services.products;

import com.prod.models.products.Review;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IReviewService {
    //create
    Review createReview(Review review);
    List<Review> createReviews(List<Review> reviews);
    //read
    Optional<Review> getReviewById(int id);
    Page<Review> getPageReviewsByProductId(int productId, int page, int size, String sortField, String sortDirect);
    Page<Review> getPageReviewsByUserId(int userId, int page, int size, String sortField, String sortDirect);
    Page<Review> getPageReviewsByValueGreaterThan(double value, int page, int size, String sortField, String sortDirect);
    Page<Review> getPageReviewsByValueLessThan(double value, int page, int size, String sortField, String sortDirect);
    Page<Review> getPageReviewsByValueBetween(double min, double max, int page, int size, String sortField, String sortDirect);
    //delete
    void deleteReviewById(int id);
}
