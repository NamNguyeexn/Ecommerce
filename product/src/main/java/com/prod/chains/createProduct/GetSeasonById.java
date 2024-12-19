package com.prod.chains.createProduct;

import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.details.Season;
import com.prod.services.details.ISeasonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetSeasonById implements ChainHandler<ProductDTO>  {
    private final ISeasonService seasonService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            Optional<Season> _season = seasonService.getSeasonById(
                    chainDataDTO.getValue().getSeason_id()
            );
            if (_season.isEmpty())
                chainDataDTO
                        .setMessage("Khong tim thay mua tuong ung")
                        .setSuccess(false);
            else{
                ProductDTO dto = chainDataDTO.getValue();
                dto.setSeason_id(_season.get().getId());
                dto.setSeason(_season.get().getName() + " " + _season.get().getYear());
                chainDataDTO.setValue(dto).setSuccess(true);
            }
        }
        return new Chain<>(this);
    }
}
