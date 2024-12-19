package com.prod.services.products.impl;

import com.prod.JPARepositories.products.LabelRepository;
import com.prod.models.products.Label;
import com.prod.services.ServicePage;
import com.prod.services.products.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.products.LabelRepository.Specs.*;
@Service
public class LabelService extends ServicePage<Label> implements ILabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Label createLabel(Label label) {
        if (label.getCreate_at() != label.getUpdate_at()) {
            label.setUpdate_at(LocalDateTime.now());
        }
        return labelRepository.save(label);
    }

    @Override
    public List<Label> createLabels(List<Label> labels) {
        return labelRepository.saveAll(labels);
    }

    @Override
    public Label updateLabel(Label label) {
        return null;
    }

    @Override
    public Optional<Label> getLabelById(int labelId) {
        return labelRepository.findById(labelId);
    }

    @Override
    public Optional<Label> getLabelByName(String labelName) {
        return labelRepository.findOne(byName(labelName));
    }

    @Override
    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    @Override
    public List<Label> getLabelsByName(String name) {
        List<Label> labels = new ArrayList<>();
        List<Label> allLabels = labelRepository.findAll();
        allLabels.stream().filter(label -> label.getName().contains(name))
                .forEach(labels::add);
        return labels;
    }

    @Override
    public Page<Label> getPageLabels(int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return labelRepository.findAll(pageable);
    }

    @Override
    public Page<Label> getPageLabelsByName(String name, int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return labelRepository.findAll(byNameLike(name),pageable);
    }

    @Override
    public void deleteLabelById(int labelId) {
        labelRepository.deleteById(labelId);
    }

    @Override
    public void deleteLabel(Label label) {
        labelRepository.delete(label);
    }
}
