package com.prod.chains.checkQuantityProduct;

import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Color;
import com.prod.services.carts.IColorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetColorById implements ChainHandler<CartDTO> {
    private final IColorService colorService;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if(!chainDataDTO.isSuccess()) {
            return new Chain<>(this);
        }
        int color_id = chainDataDTO.getValue().getColorId();
        Optional<Color> color = colorService.getColorById(color_id);
        if (color.isPresent()) {
            CartDTO dto = chainDataDTO.getValue();
            dto.setColors(color.get().getValue());
            dto.setCode(color.get().getCode());
            chainDataDTO.setValue(dto).setSuccess(true);
            return new Chain<>(this);
        } else {
            chainDataDTO.setSuccess(false);
            chainDataDTO.setMessage("Khong tim thay mau");
            return new Chain<>(this);
        }
    }
}
