package com.prod.chains.checkQuantityProduct;

import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Size;
import com.prod.services.carts.ISizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetSizeById implements ChainHandler<CartDTO> {
    private final ISizeService sizeService;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if(!chainDataDTO.isSuccess()) {
            return new Chain<>(this);
        }
        int size_id = chainDataDTO.getValue().getSizeId();
        Optional<Size> size = sizeService.getSizeById(size_id);
        if (size.isPresent()) {
            CartDTO dto = chainDataDTO.getValue();
            dto.setSize(size.get().getValue());
            chainDataDTO.setValue(dto).setSuccess(true);
            return new Chain<>(this);
        } else {
            chainDataDTO.setMessage("Khong tim thay size san pham");
            chainDataDTO.setSuccess(false);
            return new Chain<>(this);
        }
    }
}
