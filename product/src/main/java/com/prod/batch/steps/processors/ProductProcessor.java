package com.prod.batch.steps.processors;

import com.prod.batch.DTO.ProductData;
import com.prod.models.products.Product;
import com.prod.services.products.IImageService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ProductProcessor implements ItemProcessor<ProductData, Product> {
    @Autowired
    private IImageService imageService;
    @Override
    public Product process(ProductData item) throws Exception {
        return getProduct(item);
    }
    private Product getProduct(ProductData data) {
        Random random = new Random();
        double score = Math.round((random.nextDouble(0, 4) + 1) * 2) / 2.0;
        return Product.builder()
                .price(data.getPrice() * 1000L)
                .score(score)
                .review(generateReviews(score))
                .description(data.getDescription())
                .title(data.getTitle())
                .build();
    }
    private int generateReviews(double score) {
        Random random = new Random();
        int minReviews = (int) (score * 10);
        int maxReviews = (int) (score * 50);
        return random.nextInt(maxReviews - minReviews + 1) + minReviews;
    }

}
