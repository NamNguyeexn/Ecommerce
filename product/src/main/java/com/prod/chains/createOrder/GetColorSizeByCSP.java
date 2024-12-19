package com.prod.chains.createOrder;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CSPCartDTO;
import com.prod.models.carts.Color;
import com.prod.models.carts.Color_Size_Product;
import com.prod.models.carts.Size;
import com.prod.services.carts.IColorService;
import com.prod.services.carts.IColorSizeProductService;
import com.prod.services.carts.ISizeService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Builder
public class GetColorSizeByCSP implements ChainHandler<CSPCartDTO> {
    private final IColorSizeProductService cspService;
    private final IColorService colorService;
    private final ISizeService sizeService;

    @Override
    public Chain<CSPCartDTO> handle(ChainDataDTO<CSPCartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            CSPCartDTO dto = chainDataDTO.getValue();
            Optional<Color_Size_Product> csp = cspService.getColorSizeProductById(
                    dto.getCsp_id()
            );
            if (csp.isPresent()) {
                Optional<Color> color = colorService.getColorById(csp.get().getColor_id());
                Optional<Size> size = sizeService.getSizeById(csp.get().getSize_id());
                if (color.isPresent() && size.isPresent()) {
                    dto.setColor(color.get().getValue());
                    dto.setSize(size.get().getValue());
                    chainDataDTO.setSuccess(true);
                } else {
                    chainDataDTO.setMessage("Khong tim thay kich thuoc va mau").setSuccess(false);
                }

            }
        }
        return null;
    }
}
