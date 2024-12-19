package com.prod.services.carts;

import com.prod.models.carts.Cart_Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICartProductService {
    //create
    Cart_Product createCartProduct(Cart_Product cartProduct);
    //read
    Optional<Cart_Product> getCartProductById(int id);
    Optional<Cart_Product> getCartProductByCSPId(int cspId);
    List<Cart_Product> getCartProductsByCartId(int cartId);
    Optional<Cart_Product> getCartProductByCartAndCSP(int cartId, int cspId);
    Optional<Cart_Product> getCartProductByQuantity(int quantity);
    void deleteCartProduct(int id);
}
