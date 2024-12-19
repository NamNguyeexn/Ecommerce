package com.prod.chains.updateCart;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Color;
import com.prod.services.carts.IColorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CheckIfColorExist implements ChainHandler<CartDTO> {
    private final IColorService colorService;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            Optional<Color> color = colorService.getColorById(chainDataDTO.getValue().getColorId());
            if (color.isPresent()){
                CartDTO dto = chainDataDTO.getValue();
                dto.setColors(color.get().getValue());
                dto.setCode(color.get().getCode());
                chainDataDTO.setValue(dto).setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay mau").setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
