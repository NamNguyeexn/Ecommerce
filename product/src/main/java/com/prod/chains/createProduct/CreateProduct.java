package com.prod.chains.createProduct;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ColorSizeQuantityDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.details.Detail;
import com.prod.models.details.Quantity;
import com.prod.models.products.Product;
import com.prod.services.details.IDetailService;
import com.prod.services.details.IQuantityService;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Builder
public class CreateProduct extends AbstractProduct implements ChainHandler<ProductDTO>  {
    private final IProductService productService;
    private final IDetailService detailService;
    private final IQuantityService quantityService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (!chainDataDTO.isSuccess())
            return new Chain<>(this);
        Product product = Product.builder()
                .description(chainDataDTO.getValue().getDescription())
                .title(chainDataDTO.getValue().getTitle())
                .price(chainDataDTO.getValue().getPrice())
                .build();
        Product _product = productService.createProduct(product);
        if(_product == null) {
            chainDataDTO.setMessage("Khong the tao san pham")
                    .setSuccess(false);
        } else {
            Quantity quantity = createQuantity(chainDataDTO.getValue());
            Detail detail = createDetail(product.getId(), quantity.getId(), quantity.getSold(), chainDataDTO.getValue());
            ProductDTO dto = changeData(chainDataDTO.getValue(), product, quantity.getQuantity(), quantity.getSold());
            chainDataDTO.setValue(dto);
            chainDataDTO.setSuccess(true);
        }
        return new Chain<>(this);
    }

    private Quantity createQuantity(ProductDTO productDTO){
        return quantityService.createQuantity(Quantity.builder()
                .quantity(getTotalQuantity(productDTO))
                .build());
    }
    private Detail createDetail(int pId, int qId, int sold, ProductDTO productDTO){
        return detailService.createDetail(Detail.builder()
                .product_id(pId)
                .category_id(productDTO.getCategory())
                .quantity_id(qId)
                .season_id(productDTO.getSeason_id())
                .build());
    }
    private ProductDTO changeData(ProductDTO dto, Product _product, int qtt, int sold){
        dto.setProductId(_product.getId());
        dto.setTitle(_product.getTitle());
        dto.setPrice(_product.getPrice());
        dto.setSold(sold);
        dto.setQuantity(qtt);
        return dto;
    }
}
