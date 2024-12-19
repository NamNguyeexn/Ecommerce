package com.prod.mappers;

import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CartProductDTO;
import com.prod.models.products.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartProductDTO getCPDTO(CartProductDTO dataDTO);
}
