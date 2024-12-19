package com.prod.services.products.impl;

import com.prod.JPARepositories.products.LabelProductRepository;
import com.prod.models.products.Label_Product;
import com.prod.services.ServicePage;
import com.prod.services.products.ILabelProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.products.LabelProductRepository.Specs.*;
@Service
public class LabelProductService extends ServicePage<Label_Product> implements ILabelProductService {
    @Autowired
    private LabelProductRepository repository;
    @Override
    public Label_Product createLabelProduct(Label_Product labelProduct) {
        if (labelProduct.getCreate_at() != labelProduct.getUpdate_at()) {
            labelProduct.setUpdate_at(LocalDateTime.now());
        }
        return repository.save(labelProduct);
    }

    @Override
    public Optional<Label_Product> getLabelProductById(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Label_Product> getLabelProductByLabelAndProduct(int lId, int pId) {
        return repository.findOne(byLabelIdAndProductId(lId, pId));
    }

    @Override
    public List<Label_Product> getLabelProductsByLabelId(int labelId) {
        return repository.findAll(byLabelId(labelId));
    }

    @Override
    public Page<Label_Product> getPageLabelProductsByLabelId(int labelId, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return repository.findAll(byLabelId(labelId), pageable);
    }

    @Override
    public List<Label_Product> getLabelProductsByProductId(int productId) {
        return repository.findAll(byProductId(productId));
    }

    @Override
    public List<Label_Product> getLabelProducts() {
        return repository.findAll();
    }

    @Override
    public Label_Product updateLabelProduct(Label_Product labelProduct) {
        return null;
    }

    @Override
    public void deleteLabelProduct(int id) {
        repository.deleteById(id);
    }
}
