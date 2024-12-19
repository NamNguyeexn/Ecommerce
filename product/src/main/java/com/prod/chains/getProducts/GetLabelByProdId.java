package com.prod.chains.getProducts;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.products.Label;
import com.prod.models.products.Label_Product;
import com.prod.services.products.ILabelProductService;
import com.prod.services.products.ILabelService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Builder
public class GetLabelByProdId implements ChainHandler<ProductDTO> {
    private final ILabelService labelService;
    private final ILabelProductService labelProductService;

    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            List<Label_Product> _label = labelProductService.getLabelProductsByProductId(
                    chainDataDTO.getValue().getProductId()
            );
            if (!_label.isEmpty()) {
                List<String> label = new ArrayList<>();
                for (Label_Product label_product : _label) {
                    Optional<Label> labelOptional = labelService.getLabelById(label_product.getLabel_id());
                    labelOptional.ifPresent(value -> label.add(value.getName()));
                }
                ProductDTO dto = chainDataDTO.getValue();
                changeData(dto, label);
                chainDataDTO
                        .setValue(dto)
                        .setSuccess(true);
            } else chainDataDTO
                    .setMessage("Khong tim thay label")
                    .setSuccess(false);
        }
        return new Chain<>(this);
    }

    public void changeData(ProductDTO dto, List<String> labels) {
        dto.setLabel(labels);
    }
}
