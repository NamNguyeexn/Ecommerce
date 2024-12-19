package com.prod.chains.createOrder;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CSPCartDTO;
import com.prod.models.carts.Cart;
import com.prod.models.carts.Cart_Product;
import com.prod.services.carts.ICartProductService;
import com.prod.services.carts.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetCartByUserId implements ChainHandler<CSPCartDTO> {
    private final ICartService cartService;

    @Override
    public Chain<CSPCartDTO> handle(ChainDataDTO<CSPCartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            CSPCartDTO dto = chainDataDTO.getValue();
            Optional<Cart> cart = cartService.getCartByUserId(chainDataDTO.getUserId());
            if (cart.isPresent()) {
                dto.setCart_id(cart.get().getId());
                chainDataDTO
                        .setValue(dto)
                        .setSuccess(true);
            } else {
                Cart newCart = cartService.createCart(
                        Cart.builder()
                                .user_id(chainDataDTO.getUserId())
                                .build()
                );
                dto.setCart_id(newCart.getId());
                chainDataDTO
                        .setValue(dto)
                        .setSuccess(true);
            }

        }
        return new Chain<>(this);
    }
}
