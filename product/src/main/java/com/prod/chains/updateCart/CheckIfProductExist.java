package com.prod.chains.updateCart;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Color_Size_Product;
import com.prod.models.products.Product;
import com.prod.services.carts.IColorSizeProductService;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Builder
@AllArgsConstructor
public class CheckIfProductExist implements ChainHandler<CartDTO> {
    private final IColorSizeProductService cspService;
    private final IProductService productService;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            int id = chainDataDTO.getValue().getProductId();
            List<Color_Size_Product> products = cspService.getColorSizeProductsByColorIdAndSizeId(
                    chainDataDTO.getValue().getColorId(), chainDataDTO.getValue().getSizeId()
            );
            Optional<Color_Size_Product> csp = products.stream()
                    .filter(p -> p.getProduct_id() == id)
                    .findFirst();
            if (csp.isPresent()) {
                Optional<Product> product = productService.getProductById(csp.get().getProduct_id());
                if (product.isPresent()) {
                    CartDTO dto = chainDataDTO.getValue();
                    dto.setCspId(csp.get().getId());
                    dto.setProduct(product.get().getTitle());
                    dto.setPrice(product.get().getPrice());
                    chainDataDTO.setValue(dto).setSuccess(true);
                } else {
                    chainDataDTO.setMessage("Khong tim thay san pham")
                            .setSuccess(false);
                }
            } else {
                chainDataDTO.setMessage("Khong tim thay san pham voi size va mau tuong ung")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
