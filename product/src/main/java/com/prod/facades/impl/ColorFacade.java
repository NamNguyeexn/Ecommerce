package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.ColorDTO;
import com.prod.facades.IColorFacade;
import com.prod.models.carts.Color;
import com.prod.services.carts.IColorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ColorFacade extends Facade<Color> implements IColorFacade {
    @Autowired
    private IColorService colorService;
    @Override
    public ResponseObject<Color> createColor(ColorDTO colorDTO) {
        Optional<Color> color = colorService.getColorByCode(colorDTO.getCode());
        if (color.isPresent()) {
            return ResponseObject.<Color>builder()
                    .data(color.get())
                    .message("Mau nay da ton tai")
                    .isSuccess(true)
                    .build();
        }
        Color color1 = colorService.createColor(Color.builder()
                .code(colorDTO.getCode())
                .value(colorDTO.getValue())
                .build());
        return ResponseObject.<Color>builder()
                .data(color1)
                .message("Tao mau thanh cong")
                .isSuccess(true)
                .build();
    }

    @Override
    public ResponseObject<Color> updateColor(ColorDTO colorDTO) {
        Optional<Color> color = colorService.getColorById(colorDTO.getId());
        if (color.isPresent()) {
            color.get().setCode(colorDTO.getCode());
            color.get().setValue(colorDTO.getValue());
            color.get().setUpdate_at(LocalDateTime.now());
            colorService.createColor(color.get());
            return ResponseObject.<Color>builder()
                    .data(color.get())
                    .message("Cap nhat mau thanh cong")
                    .isSuccess(true)
                    .build();
        } else
            return ResponseObject.<Color>builder()
                    .message("Khong tim thay mau")
                    .build();
    }

    @Override
    public ResponseObject<Color> getColorById(int id) {
        Optional<Color> color = colorService.getColorById(id);
        if (color.isPresent()) {
            return ResponseObject.<Color>builder()
                    .data(color.get())
                    .message("Da tim thay mau thanh cong")
                    .isSuccess(true)
                    .build();
        } else
            return ResponseObject.<Color>builder()
                    .message("Khong tim thay mau")
                    .build();
    }

    @Override
    public ResponseObject<List<Color>> getColorsByValueLike(String value) {
        List<Color> color = colorService.getColorsNameLike(value);
        if (!color.isEmpty()) {
            return ResponseObject.<List<Color>>builder()
                    .data(color)
                    .message("Da tim thay danh sach mau thanh cong")
                    .isSuccess(true)
                    .build();
        } else
            return ResponseObject.<List<Color>>builder()
                    .message("Khong tim thay mau")
                    .build();
    }

    @Override
    public ResponseObject<List<Color>> getAllColors() {
        List<Color> color = colorService.getColors();
        if (!color.isEmpty()) {
            return ResponseObject.<List<Color>>builder()
                    .data(color)
                    .message("Da tim thay danh sach mau thanh cong")
                    .isSuccess(true)
                    .build();
        } else
            return ResponseObject.<List<Color>>builder()
                    .message("Khong tim thay mau")
                    .build();
    }
}
