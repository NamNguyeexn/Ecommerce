package com.prod.services.carts.impl;

import com.prod.JPARepositories.carts.CartProductRepository;
import com.prod.models.carts.Cart_Product;
import com.prod.services.carts.ICartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.carts.CartProductRepository.Specs.*;

@Service
public class CartProductService implements ICartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;

    @Override
    public Cart_Product createCartProduct(Cart_Product cartProduct) {
        if (cartProduct.getCreate_at() != cartProduct.getUpdate_at()) {
            cartProduct.setUpdate_at(LocalDateTime.now());
        }
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public Optional<Cart_Product> getCartProductById(int id) {
        return cartProductRepository.findById(id);
    }

    @Override
    public Optional<Cart_Product> getCartProductByCSPId(int cspId) {
        return cartProductRepository.findOne(byColorSizeProductId(cspId));
    }

    @Override
    public List<Cart_Product> getCartProductsByCartId(int cartId) {
        return cartProductRepository.findAll(byCartId(cartId));
    }

    @Override
    public Optional<Cart_Product> getCartProductByCartAndCSP(int cartId, int cspId) {
        return cartProductRepository.findOne(byCartId(cartId).and(byColorSizeProductId(cspId)));
    }

    /**
     * Hàm này sử dụng để tìm kiếm các sản phẩm đã hết
     */
    @Override
    public Optional<Cart_Product> getCartProductByQuantity(int quantity) {
        return cartProductRepository.findOne(byQuantity(quantity));
    }

    @Override
    public void deleteCartProduct(int id) {
        cartProductRepository.deleteById(id);
    }
}
