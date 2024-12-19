package com.prod.services.products;

import com.prod.models.products.Label;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ILabelService {
    //create
    Label createLabel(Label label);
    List<Label> createLabels(List<Label> labels);
    //update
    Label updateLabel(Label label);
    //read
    Optional<Label> getLabelById(int labelId);
    Optional<Label> getLabelByName(String labelName);
    List<Label> getLabels();
    List<Label> getLabelsByName(String name);
    Page<Label> getPageLabels(int page, int size, String field, String direct);
    Page<Label> getPageLabelsByName(String name, int page, int size, String field, String direct);
    //delete
    void deleteLabelById(int labelId);
    void deleteLabel(Label label);
}
