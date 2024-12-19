package com.prod.chains.getProducts;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.details.Detail;
import com.prod.models.details.Quantity;
import com.prod.services.details.IDetailService;
import com.prod.services.details.IQuantityService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Builder
@AllArgsConstructor
public class GetDetailByProdId implements ChainHandler<ProductDTO> {
    private final IDetailService detailService;
    private final IQuantityService quantityService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            Optional<Detail> _detail = detailService.getDetailByProductId(
                    chainDataDTO.getValue().getProductId()
            );
            if (_detail.isPresent()){
                ProductDTO dto = chainDataDTO.getValue();
                changeData(dto, _detail.get());
                chainDataDTO.setValue(dto)
                        .setSuccess(true);
            }
            else {
                chainDataDTO.setMessage("Khong tim thay detail")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
    private void changeData(ProductDTO dto, Detail detail){
        dto.setCategory(detail.getCategory_id());
        dto.setSeason_id(detail.getSeason_id());
        Optional<Quantity> quantity = quantityService.getQuantityById(detail.getQuantity_id());
        if (quantity.isPresent()){
            dto.setQuantity(quantity.get().getQuantity());
            dto.setSold(quantity.get().getSold());
        }
    }
}
