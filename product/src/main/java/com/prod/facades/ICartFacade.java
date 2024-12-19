package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.facades.DTO.CartDTO;
import com.prod.facades.DTO.CartProductDTO;
import com.prod.models.carts.Cart;
import com.prod.models.carts.Cart_Product;
import com.prod.models.carts.Color;
import com.prod.models.carts.Color_Size_Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICartFacade {
    ResponseObject<Page<CartDTO>> getCartByUserId(int userId, AccountDTO accountDTO, int page, int size);
    ResponseObject<Page<Color_Size_Product>> getCSP(int cartId, int page, int size, String field, String direct);
    ResponseObject<Page<CartDTO>> updateCartByUserId(List<CartProductDTO> cartProductDTO, int userId, int page, int size);
}
