package com.prod.chains.getProducts;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.products.Product;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetProdById implements ChainHandler<ProductDTO> {
    private final IProductService productService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            Optional<Product> _product = productService.getProductById(
                    chainDataDTO.getValue().getProductId()
            );
            if (_product.isPresent()){
                ProductDTO dto = chainDataDTO.getValue();
                changeData(_product.get(), dto);
                chainDataDTO.setValue(dto)
                        .setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay san pham")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
    private void changeData(Product product, ProductDTO productDTO){
        productDTO.setProductId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setScore(product.getScore());
        productDTO.setReviews(product.getReview());
        productDTO.setDescription(product.getDescription());
    }
}
