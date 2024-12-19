package com.prod.services.caches;

import com.prod.models.products.Product;
import org.springframework.stereotype.Service;

@Service
public interface IEmailSender {
    void outOfProduct(String email, String product);
    void lowQuantityProduct(String email, String product, int quantity);
}
