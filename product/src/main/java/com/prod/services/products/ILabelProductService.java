package com.prod.services.products;

import com.prod.models.products.Label_Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ILabelProductService {
    //create
    Label_Product createLabelProduct(Label_Product labelProduct);
    //read
    Optional<Label_Product> getLabelProductById(int id);
    Optional<Label_Product> getLabelProductByLabelAndProduct(int lId, int pId);
    List<Label_Product> getLabelProductsByLabelId(int labelId);
    Page<Label_Product> getPageLabelProductsByLabelId(int labelId, int page, int size, String sortField, String sortDirect);
    List<Label_Product> getLabelProductsByProductId(int productId);
    List<Label_Product> getLabelProducts();
    //update
    Label_Product updateLabelProduct(Label_Product labelProduct);
    void deleteLabelProduct(int id);
}
