package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.Chain;
import com.prod.chains.checkQuantityProduct.*;
import com.prod.chains.updateCart.*;
import com.prod.facades.DTO.CartDTO;
import com.prod.facades.DTO.CartProductDTO;
import com.prod.facades.ICartFacade;
import com.prod.mappers.CartMapper;
import com.prod.models.carts.Cart;
import com.prod.models.carts.Cart_Product;
import com.prod.models.carts.Color_Size_Product;
import com.prod.services.caches.IEmailSender;
import com.prod.services.carts.*;
import com.prod.services.details.IDetailService;
import com.prod.services.details.IQuantityService;
import com.prod.services.products.IImageService;
import com.prod.services.products.IProductService;
import com.prod.utils.ConvertListPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartFacade implements ICartFacade {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ICartProductService cartProductService;
    @Autowired
    private IColorSizeProductService cspService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IDetailService detailService;
    @Autowired
    private IQuantityService quantityService;
    @Autowired
    private ISmallQuantityService smallQuantityService;
    @Autowired
    private IEmailSender emailSender;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ConvertListPage<CartDTO> convertListPage;

    @Override
    public ResponseObject<Page<CartDTO>> getCartByUserId(int userId, AccountDTO accountDTO, int page, int size) {
        try {
            Optional<Cart> cart = cartService.getCartByUserId(userId);
            if (cart.isPresent()) {
                List<Cart_Product> cspList = cartProductService.getCartProductsByCartId(cart.get().getId());
                if (cspList == null) {
                    return ResponseObject.<Page<CartDTO>>builder()
                            .message("Gio hang trong")
                            .build();
                } else {
                    List<CartDTO> list = new ArrayList<>();
                    cspList.sort(Comparator.comparing(Cart_Product::getUpdate_at).reversed());
                    for (Cart_Product csp : cspList) {
                        list.add(alertEmptyQuantity(csp, accountDTO.getEmail()));
                    }
                    return ResponseObject.<Page<CartDTO>>builder()
                            .data(convertListPage.listToPage(list, page, size))
                            .message("Da tim thay gio hang")
                            .isSuccess(true)
                            .build();
                }
            } else {
                return ResponseObject.<Page<CartDTO>>builder()
                        .message("Khong tim thay gio hang")
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseObject.<Page<CartDTO>>builder()
                    .message("Get Cart Products By Cart Id - Gap loi server")
                    .build();
        }
    }

    @Override
    public ResponseObject<Page<Color_Size_Product>> getCSP(int cartId, int page, int size, String sortField, String sortDirection) {
        return null;
    }

    @Override
    public ResponseObject<Page<CartDTO>> updateCartByUserId(List<CartProductDTO> cartProductDTO, int userId, int page, int size) {
        try {
            Cart cart = cartService.getCartByUserId(userId).isEmpty() ? cartService.createCart(Cart.builder()
                    .user_id(userId)
                    .build()) : cartService.getCartByUserId(userId).get();
            List<CartDTO> res = new ArrayList<>();
            for (CartProductDTO cartDTO : cartProductDTO) {
                ChainDataDTO<CartDTO> dto = ChainDataDTO.<CartDTO>builder()
                        .userId(userId)
                        .value(
                                CartDTO.builder()
                                        .quantity(cartDTO.getQuantity())
                                        .productId(cartDTO.getProductId())
                                        .colorId(cartDTO.getColorId())
                                        .sizeId(cartDTO.getSizeId())
                                        .build()
                        )
                        .cartId(cart.getId())
                        .build();
                Chain<CartDTO> chain = new Chain<CartDTO>()
                        .add(new CheckIfColorExist(colorService))
                        .add(new CheckIfSizeExist(sizeService))
                        .add(new GetFirstImageByProdId(imageService))
                        .add(new CheckIfProductExist(cspService, productService))
                        .add(new CheckIfQuantityEnough(smallQuantityService, cartProductService, cartService));
                chain.execute(dto);
                if (!dto.isSuccess()) log.error(dto.getMessage());
                else res.add(dto.getValue());
            }
            if (res.isEmpty()) {
                return ResponseObject.<Page<CartDTO>>builder()
                        .message("Khong the tao gio hang")
                        .build();
            } else {
                return ResponseObject.<Page<CartDTO>>builder()
                        .isSuccess(true)
                        .message("Cap nhat gio hang thanh cong")
                        .data(convertListPage.listToPage(res, page, size))
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseObject.<Page<CartDTO>>builder()
                    .message("Gap loi server")
                    .build();
        }
    }

    private CartDTO alertEmptyQuantity(Cart_Product cartProduct, String email) {
        Optional<Color_Size_Product> csp = cspService.getColorSizeProductById(cartProduct.getColor_size_product_id());
        if (csp.isPresent()) {
            ChainDataDTO<CartDTO> dataDTO = ChainDataDTO.<CartDTO>builder()
                    .value(CartDTO.builder()
                            .cspId(csp.get().getId())
                            .sizeId(csp.get().getSize_id())
                            .colorId(csp.get().getColor_id())
                            .cart_product_id(cartProduct.getId())
                            .productId(csp.get().getProduct_id())
                            .build())
                    .userEmail(email)
                    .build();
            Chain<CartDTO> chain = new Chain<CartDTO>()
                    .add(new GetSizeById(sizeService))
                    .add(new GetColorById(colorService))
                    .add(new GetFirstImageByProdId(imageService))
                    .add(new GetCartProductById(productService, cartProductService))
                    .add(new GetSmallQuantityByCSPId(smallQuantityService, emailSender));
            chain.execute(dataDTO);
            if (dataDTO.isSuccess()) {
                return dataDTO.getValue();
            } else {
                log.error(dataDTO.getMessage());
                return null;
            }
        } else {
            log.error("Khong tim thay id color size product");
            return null;
        }
    }
}
