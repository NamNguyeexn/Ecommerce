package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.SizeDTO;
import com.prod.facades.ISizeFacade;
import com.prod.models.carts.Size;
import com.prod.services.carts.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SizeFacade extends Facade<Size> implements ISizeFacade {
    @Autowired
    private ISizeService sizeService;
    @Override
    public ResponseObject<Size> createSize(SizeDTO sizeDTO) {
        Optional<Size> size = sizeService.getSizeByValue(sizeDTO.getValue());
        if (size.isPresent()) {
            return ResponseObject.<Size>builder()
                    .message("Da ton tai size")
                    .data(size.get())
                    .isSuccess(true)
                    .build();
        } else {
            Size newSize = sizeService.createSize(Size.builder()
                    .value(sizeDTO.getValue())
                    .build());
            return ResponseObject.<Size>builder()
                    .message("Tao size thanh cong")
                    .data(newSize)
                    .isSuccess(true)
                    .build();
        }
    }

    @Override
    public ResponseObject<Size> updateSize(SizeDTO sizeDTO) {
        Optional<Size> size = sizeService.getSizeById(sizeDTO.getId());
        if (size.isPresent()) {
            size.get().setValue(sizeDTO.getValue());
            size.get().setUpdate_at(LocalDateTime.now());
            Size newSize = sizeService.createSize(size.get());
            return ResponseObject.<Size>builder()
                    .message("Da ton tai size")
                    .data(newSize)
                    .isSuccess(true)
                    .build();
        } else {
            return ResponseObject.<Size>builder()
                    .message("Tao size thanh cong")
                    .build();
        }
    }

    @Override
    public ResponseObject<Size> getSizeById(int id) {
        Optional<Size> size = sizeService.getSizeById(id);
        if (size.isPresent()) {
            return ResponseObject.<Size>builder()
                    .message("Da tim thay size")
                    .data(size.get())
                    .isSuccess(true)
                    .build();
        } else {
            return ResponseObject.<Size>builder()
                    .message("Khong tim thay size")
                    .build();
        }
    }

    @Override
    public ResponseObject<List<Size>> getAllSizes() {
        List<Size> size = sizeService.getAllSizes();
        if (!size.isEmpty()) {
            return ResponseObject.<List<Size>>builder()
                    .message("Da tim thay danh sach size")
                    .data(size)
                    .isSuccess(true)
                    .build();
        } else {
            return ResponseObject.<List<Size>>builder()
                    .message("Khong tim thay danh sach size")
                    .build();
        }
    }
}
