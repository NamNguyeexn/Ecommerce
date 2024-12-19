package com.prod.chains.createProduct;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
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

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
@Builder
public class UpdateProduct extends AbstractProduct implements ChainHandler<ProductDTO> {
    private final IProductService productService;
    private final IDetailService detailService;
    private final IQuantityService quantityService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            Optional<Product> _product = productService.getProductById(
                    chainDataDTO.getValue().getProductId()
            );
            if (_product.isEmpty())
                chainDataDTO.setMessage("Khong tim thay san pham")
                        .setSuccess(false);
            else {
                ProductDTO dto = chainDataDTO.getValue();
                updateProduct(dto, _product.get());
                Optional<Detail> detail = detailService.getDetailByProductId(_product.get().getId());
                if (detail.isPresent()) {
                    Optional<Quantity> quantity = quantityService.getQuantityById(detail.get().getQuantity_id());
                    if (quantity.isPresent()) {
                        Quantity update = changeData(chainDataDTO.getValue(), detail.get(), quantity.get());
                        dto.setSold(update.getSold());
                        dto.setQuantity(update.getQuantity());
                        chainDataDTO.setValue(dto).setSuccess(true);
                    } else {
                        chainDataDTO.setMessage("Khong tim thay so luong san pham trong kho")
                                .setSuccess(false);
                    }
                } else {
                    chainDataDTO.setMessage("Khong tim thay chi tiet san pham")
                            .setSuccess(false);
                }
            }
        }
        return new Chain<>(this);
    }

    private Quantity changeData(ProductDTO dto, Detail detail, Quantity quantity){
        detail.setSeason_id(dto.getSeason_id());
        detail.setCategory_id(dto.getCategory());
        quantity.setQuantity(getTotalQuantity(dto));
        quantity.setUpdate_at(LocalDateTime.now());
        detailService.createDetail(detail);
        return quantityService.createQuantity(quantity);
    }
    private void updateProduct(ProductDTO dto, Product product){
        product.setId(dto.getProductId());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUpdate_at(LocalDateTime.now());
        productService.createProduct(product);
    }
}
