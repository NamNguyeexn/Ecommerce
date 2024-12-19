package com.prod.chains.createOrder;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CSPCartDTO;
import com.prod.models.carts.Color_Size_Product;
import com.prod.services.carts.IColorService;
import com.prod.services.carts.IColorSizeProductService;
import com.prod.services.carts.ISizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetCSPById implements ChainHandler<CSPCartDTO> {
    private final IColorSizeProductService colorSizeProductService;
    @Override
    public Chain<CSPCartDTO> handle(ChainDataDTO<CSPCartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            CSPCartDTO dto = chainDataDTO.getValue();
            Optional<Color_Size_Product> csp = colorSizeProductService.getColorSizeProductById(dto.getCsp_id());
            if (csp.isPresent()) {
                dto.setProduct_id(csp.get().getProduct_id());
                chainDataDTO
                        .setValue(dto)
                        .setSuccess(true);
            } else {
                chainDataDTO
                        .setMessage("Khong tim thay san pham trong kho")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
