package com.prod.services.products.impl;

import com.prod.JPARepositories.products.ProductRepository;
import com.prod.models.products.Product;
import com.prod.models.products.Review;
import com.prod.services.ServicePage;
import com.prod.services.products.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.prod.JPARepositories.products.ProductRepository.Specs.*;

@Service
public class ProductService extends ServicePage<Product> implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        if (product.getCreate_at() != product.getUpdate_at()) {
            product.setUpdate_at(LocalDateTime.now());
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsByTitle(String title) {
        return productRepository.findAll(byTitle(title));
    }

    @Override
    public Page<Product> findProducts(Set<String> key, int page, int size, String sortField, String sortDirection) {
        String keywords = String.join(" ", key);
        Specification<Product> keySpecs = Specification.where(
                byKey(key));
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirection), sortField);
        Page<Product> products = productRepository.findAll(keySpecs, pageable);
        Page<Product> productPage = productRepository.findAll(
                Specification.where(byDesLike(keywords).or(byTitleLike(keywords))), pageable
        );
        if (products.hasContent()) {
            return products;
        } else {
            return productPage;
        }
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getProductsByPage(int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return productRepository.findAll(pageable);
    }


    @Override
    public List<Product> getProductsByScore(double score) {
        return productRepository.findAll(byScore(score));
    }

    @Override
    public List<Product> getProductsByScoreAndTitle(String name, double score) {
        return productRepository.findAll(byScoreAndByTitle(name, score));
    }

    @Override
    public List<Product> getProductsByPriceRange(long lower, long upper) {
        return productRepository.findAll(byPriceInRange(lower, upper));
    }

    @Override
    public List<Product> getProductsByPriceRangeAndTitle(String name, long lower, long upper) {
        return productRepository.findAll(byPriceInRangeAndByTitle(lower, upper, name));
    }

    @Override
    public List<Product> getProductsByPriceGreaterThan(long price) {
        return productRepository.findAll(byPriceGreater(price));
    }

    @Override
    public List<Product> getProductsByPriceLessThan(long price) {
        return productRepository.findAll(byPriceLess(price));
    }

    @Override
    public void updateProductWithReviews(int id, Review review) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setUpdate_at(LocalDateTime.now());
            double score = product.get().getScore();
            int reviews = product.get().getReview();
            product.get().setReview(reviews + 1);
            product.get().setScore((reviews * score + review.getValue())/(reviews + 1));
            productRepository.save(product.get());
        }
    }


    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
