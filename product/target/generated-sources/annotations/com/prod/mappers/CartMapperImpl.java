package com.prod.mappers;

import com.prod.facades.DTO.CartProductDTO;
import com.prod.facades.DTO.CartProductDTO.CartProductDTOBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-11T20:01:07+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartProductDTO getCPDTO(CartProductDTO dataDTO) {
        if ( dataDTO == null ) {
            return null;
        }

        CartProductDTOBuilder cartProductDTO = CartProductDTO.builder();

        cartProductDTO.quantity( dataDTO.getQuantity() );
        cartProductDTO.productId( dataDTO.getProductId() );
        cartProductDTO.colorId( dataDTO.getColorId() );
        cartProductDTO.sizeId( dataDTO.getSizeId() );

        return cartProductDTO.build();
    }
}
