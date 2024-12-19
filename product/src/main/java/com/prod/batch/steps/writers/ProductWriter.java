package com.prod.batch.steps.writers;

import com.prod.JPARepositories.products.ProductRepository;
import com.prod.models.products.Product;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductWriter {
    @Autowired
    private ProductRepository productRepository;
    public RepositoryItemWriter<Product> writer() {
        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        writer.setRepository(productRepository);
        return writer;
    }
}
