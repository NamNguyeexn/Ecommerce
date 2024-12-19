package com.prod.chains.updateCart;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Size;
import com.prod.services.carts.ISizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CheckIfSizeExist implements ChainHandler<CartDTO> {
    private final ISizeService sizeService;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            Optional<Size> size = sizeService.getSizeById(chainDataDTO.getValue().getSizeId());
            if (size.isPresent()){
                CartDTO dto = chainDataDTO.getValue();
                dto.setSize(size.get().getValue());
                chainDataDTO.setValue(dto);
                chainDataDTO.setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay size san pham")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
