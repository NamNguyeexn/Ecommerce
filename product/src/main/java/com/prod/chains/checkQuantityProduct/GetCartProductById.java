package com.prod.chains.checkQuantityProduct;

import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Cart_Product;
import com.prod.models.products.Product;
import com.prod.services.carts.ICartProductService;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@AllArgsConstructor
public class GetCartProductById implements ChainHandler<CartDTO> {
    private final IProductService productService;
    private final ICartProductService cartProductService;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if (!chainDataDTO.isSuccess()) {
            return new Chain<>(this);
        }
        Optional<Product> product = productService.getProductById(
                chainDataDTO.getValue().getProductId()
        );
        Optional<Cart_Product> cartProduct = cartProductService.getCartProductById(
                chainDataDTO.getValue().getCart_product_id()
        );
        if (product.isPresent() && cartProduct.isPresent()) {
            CartDTO dto = chainDataDTO.getValue();
            dto.setProduct(product.get().getTitle());
            dto.setPrice(product.get().getPrice());
            dto.setQuantity(cartProduct.get().getQuantity());
            chainDataDTO.setValue(dto).setSuccess(true);
        } else {
            chainDataDTO.setMessage("Khong tim thay san pham/gio hang").setSuccess(false);
        }
        return new Chain<>(this);
    }

}
