package com.prod.chains.getProducts;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.products.Product;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@AllArgsConstructor
@Builder
public class CreateSignature implements ChainHandler<ProductDTO> {
    private final IProductService productService;

    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            ProductDTO dto = chainDataDTO.getValue();
            List<String> sig = new ArrayList<>();
            Optional<Product> product = productService.getProductById(dto.getProductId());
            if (product.isPresent()) {
                initSignature(dto, sig);
                getSignature(dto, sig, product.get());
            }
        }
        return null;
    }

    private void initSignature(ProductDTO productDTO, List<String> sig) {
        sig.addAll(List.of(productDTO.getSeason().split(" ")));
        sig.addAll(List.of(productDTO.getCategoryName().split(" ")));
        sig.addAll(productDTO.getLabel());
    }

    private void getSignature(ProductDTO dto, List<String> sig, Product product) {
        Set<String> set = new LinkedHashSet<>();
        for (String s : sig) {
            set.add(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
        }
        String signature = String.join(" ", set);
        product.setSignature(signature);
        product.setUpdate_at(LocalDateTime.now());
        productService.createProduct(product);
    }
}
