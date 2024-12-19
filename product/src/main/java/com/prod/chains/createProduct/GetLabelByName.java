package com.prod.chains.createProduct;

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
public class GetLabelByName implements ChainHandler<ProductDTO> {
    private final ILabelService labelService;
    private final ILabelProductService labelProductService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            ProductDTO dto = chainDataDTO.getValue();
            List<Label_Product> labelProducts = labelProductService.getLabelProductsByProductId(
                    chainDataDTO.getValue().getProductId()
            );
            List<Label> labels = getLabelByName(chainDataDTO.getValue().getLabel());
            if (labelProducts.isEmpty()) {
                List<String> labelsName = createListLabel(labels, chainDataDTO.getValue().getProductId());
                dto.setLabel(labelsName);
                chainDataDTO.setValue(dto).setSuccess(true);
            } else {
                for (Label_Product labelProduct : labelProducts) {
                    labelProductService.deleteLabelProduct(labelProduct.getId());
                }
                List<String> labelsName = createListLabel(labels, chainDataDTO.getValue().getProductId());
                dto.setLabel(labelsName);
                chainDataDTO.setValue(dto).setSuccess(true);
            }
        }
        return new Chain<>(this);
    }

    private List<Label> getLabelByName(List<String> labels){
        List<Label> res = new ArrayList<>();
        for (String label : labels) {
            Optional<Label> labelOptional = labelService.getLabelByName(label);
            labelOptional.ifPresent(res::add);
        }
        return res;
    }
    private List<String> createListLabel(List<Label> labels, int productId){
        for (Label label : labels) {
            labelProductService.createLabelProduct(
                    Label_Product.builder()
                            .label_id(label.getId())
                            .product_id(productId)
                            .build()
            );
        }
        return labels.stream().map(Label::getName).toList();
    }
}