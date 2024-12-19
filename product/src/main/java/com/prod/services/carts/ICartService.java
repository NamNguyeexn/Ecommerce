package com.prod.services.carts;

import com.prod.models.carts.Cart;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ICartService {
    //create
    Cart createCart(Cart cart);
    //update
    Cart updateCart(Cart cart);
    //read
    Optional<Cart> getCartById(int id);
    Optional<Cart> getCartByUserId(int userId);
    //delete
    void deleteCartById(int id);
    void deleteCart(Cart cart);
}
