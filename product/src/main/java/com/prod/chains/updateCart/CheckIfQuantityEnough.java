package com.prod.chains.updateCart;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Cart_Product;
import com.prod.models.carts.Small_Quantity;
import com.prod.services.carts.ICartProductService;
import com.prod.services.carts.ICartService;
import com.prod.services.carts.ISmallQuantityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CheckIfQuantityEnough implements ChainHandler<CartDTO> {
    private final ISmallQuantityService smallQuantityService;
    private final ICartProductService cartProductService;
    private final ICartService cartService;

    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            Optional<Small_Quantity> sm = smallQuantityService.getByCSProductId(chainDataDTO.getValue().getCspId());
            if (sm.isPresent()) {
                if (sm.get().getQuantity() > chainDataDTO.getValue().getQuantity()) {
                    CartDTO dto = chainDataDTO.getValue();
                    List<Cart_Product> cp = cartProductService.getCartProductsByCartId(chainDataDTO.getCartId());
                    if (!cp.isEmpty()) {
                        List<Integer> idCP = cp.stream().map(Cart_Product::getColor_size_product_id).toList();
                        for (Cart_Product c : cp) {
                            if (idCP.contains(dto.getCspId())) {
                                Optional<Cart_Product> _cart_product = cartProductService.getCartProductByCartAndCSP(
                                        chainDataDTO.getCartId(), dto.getCspId()
                                );
                                if (_cart_product.isPresent() && _cart_product.get().getId() == c.getId()) {
                                    CartDTO updateDto = updateCartProduct(dto, c);
                                    chainDataDTO.setValue(updateDto).setSuccess(true);
                                    break;
                                }
                            } else {
                                CartDTO createDto = createCartProduct(chainDataDTO);
                                chainDataDTO.setValue(createDto).setSuccess(true);
                                break;
                            }
                        }
                    } else {
                        CartDTO createDto = createCartProduct(chainDataDTO);
                        chainDataDTO.setValue(createDto).setSuccess(true);
                    }
                } else {
                    chainDataDTO.setMessage("So luong trong kho khong du")
                            .setSuccess(false);
                }
            } else {
                chainDataDTO.setMessage("Khong tim thay so luong san pham trong kho")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }

    private CartDTO createCartProduct(ChainDataDTO<CartDTO> dto) {
        Cart_Product cart_product = cartProductService.createCartProduct(
                Cart_Product.builder()
                        .quantity(dto.getValue().getQuantity())
                        .cart_id(dto.getCartId())
                        .color_size_product_id(dto.getValue().getCspId())
                        .build()
        );
        CartDTO cartDTO = dto.getValue();
        cartDTO.setCart_product_id(cart_product.getId());
        return cartDTO;
    }
    private CartDTO updateCartProduct(CartDTO dto, Cart_Product c) {
        c.setQuantity(dto.getQuantity());
        c.setUpdate_at(LocalDateTime.now());
        Cart_Product updateCartProduct = cartProductService.createCartProduct(c);
        dto.setCart_product_id(updateCartProduct.getId());
        dto.setQuantity(updateCartProduct.getQuantity());
        return dto;
    }
}