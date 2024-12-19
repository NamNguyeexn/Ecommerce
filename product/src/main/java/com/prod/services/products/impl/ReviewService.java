package com.prod.services.products.impl;

import com.prod.JPARepositories.products.ReviewRepository;
import com.prod.models.products.Review;
import com.prod.services.ServicePage;
import com.prod.services.products.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.products.ReviewRepository.Specs.*;

@Service
public class ReviewService extends ServicePage<Review> implements IReviewService {
    @Autowired
    private ReviewRepository repository;

    @Override
    public Review createReview(Review review) {
        return repository.save(review);
    }

    @Override
    public List<Review> createReviews(List<Review> reviews) {
        return repository.saveAll(reviews);
    }

    @Override
    public Optional<Review> getReviewById(int id) {
        return repository.findById(id);
    }

    @Override
    public Page<Review> getPageReviewsByProductId(int productId, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return repository.findAll(byProductId(productId),pageable);
    }

    @Override
    public Page<Review> getPageReviewsByUserId(int userId, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return repository.findAll(byUserId(userId),pageable);
    }

    @Override
    public Page<Review> getPageReviewsByValueGreaterThan(double value, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return repository.findAll(byValueGreaterThan(value),pageable);
    }

    @Override
    public Page<Review> getPageReviewsByValueLessThan(double value, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return repository.findAll(byValueGreaterThan(value),pageable);
    }

    @Override
    public Page<Review> getPageReviewsByValueBetween(double min, double max, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return repository.findAll(byValueInRange(min, max),pageable);
    }


    @Override
    public void deleteReviewById(int id) {
        repository.deleteById(id);
    }
}
