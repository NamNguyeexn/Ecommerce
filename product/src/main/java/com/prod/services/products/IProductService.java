package com.prod.services.products;

import com.prod.models.products.Product;
import com.prod.models.products.Review;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface IProductService {
    //create
    Product createProduct(Product product);
    List<Product> createProducts(List<Product> products);
    //read
    Optional<Product> getProductById(int id);
    List<Product> getProductsByTitle(String title);
    Page<Product> findProducts(Set<String> key, int page, int size, String sortField, String sortDirection);
    List<Product> getProducts();
    Page<Product> getProductsByPage(int page, int size, String sortField, String sortDirection);
    List<Product> getProductsByScore(double score);
    List<Product> getProductsByScoreAndTitle(String name, double score);
    List<Product> getProductsByPriceRange(long lower, long upper);
    List<Product> getProductsByPriceRangeAndTitle(String name, long lower, long upper);
    List<Product> getProductsByPriceGreaterThan(long price);
    List<Product> getProductsByPriceLessThan(long price);
    //update
    void updateProductWithReviews(int id, Review review);
    //delete
    void deleteProduct(int id);

}
