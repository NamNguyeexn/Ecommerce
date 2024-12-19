package com.prod.services.carts.impl;

import com.prod.JPARepositories.carts.CartRepository;
import com.prod.models.carts.Cart;
import com.prod.services.carts.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.prod.JPARepositories.carts.CartRepository.Specs.*;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Cart cart) {
        return null;
    }

    @Override
    public Optional<Cart> getCartById(int id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Cart> getCartByUserId(int userId) {
        return cartRepository.findOne(byUserId(userId));
    }

    @Override
    public void deleteCartById(int id) {
        cartRepository.deleteById(id);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }
}
