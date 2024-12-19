package com.prod.chains.getProducts;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ColorSizeQuantityDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.carts.Color;
import com.prod.models.carts.Color_Size_Product;
import com.prod.models.carts.Size;
import com.prod.models.carts.Small_Quantity;
import com.prod.models.products.Product;
import com.prod.services.carts.IColorService;
import com.prod.services.carts.IColorSizeProductService;
import com.prod.services.carts.ISizeService;
import com.prod.services.carts.ISmallQuantityService;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@AllArgsConstructor
@Builder
public class GetColorSizeQuantityByProdId implements ChainHandler<ProductDTO> {
    private final IColorSizeProductService cspService;
    private final ISmallQuantityService smallQuantityService;
    private final IColorService colorService;
    private final ISizeService sizeService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            List<Color_Size_Product> csps = cspService.getColorSizeProductsByProductId(
                    chainDataDTO.getValue().getProductId()
            );
            ProductDTO productDTO = chainDataDTO.getValue();
            if (csps != null) {
                csps.forEach(csp -> {
                    changeData(productDTO, csp);
                });
                chainDataDTO.setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay color size product")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }

    private void changeData(ProductDTO productDTO, Color_Size_Product csp) {
        ColorSizeQuantityDTO csqDTO = new ColorSizeQuantityDTO();
        //quantity
        Optional<Small_Quantity> quantity = smallQuantityService.getByCSProductId(csp.getId());
        quantity.ifPresent(smallQuantity -> {
            int stock = smallQuantity.getQuantity()==0?0:Math.abs(smallQuantity.getQuantity()-smallQuantity.getSold());
            csqDTO.setQuantity(smallQuantity.getQuantity());
            csqDTO.setSold(smallQuantity.getSold());
        });
        //color
        Optional<Color> color = colorService.getColorById(csp.getColor_id());
        color.ifPresent(c -> {
            csqDTO.setColor(c.getValue());
            csqDTO.setCode(c.getCode());
            csqDTO.setColor_id(c.getId());
        });
        //size
        Optional<Size> size = sizeService.getSizeById(csp.getSize_id());
        size.ifPresent(s -> {
            csqDTO.setSize(s.getValue());
            csqDTO.setSize_id(s.getId());
        });
        List<ColorSizeQuantityDTO> csqList = productDTO.getCsq();
        if (csqList == null) {
            csqList = new ArrayList<>();
        }
        csqList.add(csqDTO);
        productDTO.setCsq(csqList);
    }
}
