package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.LabelDTO;
import com.prod.facades.ILabelFacade;
import com.prod.models.products.Label;
import com.prod.services.products.ILabelService;
import com.prod.utils.ConvertListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LabelFacade implements ILabelFacade {
    @Autowired
    private ILabelService labelService;
    @Autowired
    private ConvertListPage<Label> convertListPage;

    @Override
    public ResponseObject<Label> createLabel(LabelDTO label) {
        Optional<Label> _label = labelService.getLabelByName(label.getName());
        if (_label.isPresent()) {
            return ResponseObject.<Label>builder()
                    .data(_label.get())
                    .message("Label da ton tai")
                    .isSuccess(false)
                    .build();
        }
        Label newLabel = Label.builder()
                .name(label.getName())
                .build();
        return ResponseObject.<Label>builder()
                .data(labelService.createLabel(newLabel))
                .message("Tao label thanh cong")
                .isSuccess(true)
                .build();
    }

    @Override
    public ResponseObject<Label> getLabel(int id) {
        Optional<Label> _label = labelService.getLabelById(id);
        if (_label.isPresent()) {
            return ResponseObject.<Label>builder()
                    .data(_label.get())
                    .message("Tim thay label thanh cong")
                    .isSuccess(true)
                    .build();
        } else {
            return ResponseObject.<Label>builder()
                    .message("Khong tim thay label")
                    .build();
        }
    }

    @Override
    public ResponseObject<Page<Label>> getAllLabels(int page, int limit, String field, String direct) {
        Page<Label> labels = labelService.getPageLabels(page, limit, field, direct);
        if (labels.getContent().isEmpty()) {
            return ResponseObject.<Page<Label>>builder()
                    .message("Danh sach label trong")
                    .build();
        } else
            return ResponseObject.<Page<Label>>builder()
                    .data(labels)
                    .message("Lay danh sach label thanh cong")
                    .isSuccess(true)
                    .build();
    }

    @Override
    public ResponseObject<Page<Label>> getAllLabelsByName(String name, int page, int limit, String field, String direct) {
        Page<Label> labels = labelService.getPageLabelsByName(name, page, limit, field, direct);
        if (labels.getContent().isEmpty()) {
            return ResponseObject.<Page<Label>>builder()
                    .message("Khong tim thay danh sach label phu hop")
                    .build();
        } else
            return ResponseObject.<Page<Label>>builder()
                    .data(labels)
                    .isSuccess(true)
                    .message("Lay danh sach label thanh cong")
                    .build();
    }

//    @Override
//    public ResponseObject<Boolean> deleteLabel(LabelDTO label) {
//        try {
//            labelService.deleteLabel(Label.builder()
//                    .name(label.getName())
//                    .build());
//            boolean res = labelService.getLabelByName(label.getName()).isPresent();
//            return ResponseObject.<Boolean>builder()
//                    .data(res)
//                    .message("Da thuc hien xoa label")
//                    .isSuccess(res)
//                    .build();
//        } catch (Exception e){
//            return ResponseObject.<Boolean>builder()
//                    .message("Server gap loi")
//                    .build();
//        }
//    }

    @Override
    public ResponseObject<Label> updateLabel(LabelDTO label) {
        Optional<Label> _label = labelService.getLabelById(label.getId());
        if (_label.isPresent()) {
            _label.get().setName(label.getName());
            _label.get().setUpdate_at(LocalDateTime.now());
            Label newLabel = labelService.createLabel(_label.get());
            return ResponseObject.<Label>builder()
                    .message("Cap nhat label thanh cong")
                    .isSuccess(true)
                    .data(newLabel)
                    .build();
        } else {
            return ResponseObject.<Label>builder()
                    .message("Khong tim thay label")
                    .build();
        }
    }
}
