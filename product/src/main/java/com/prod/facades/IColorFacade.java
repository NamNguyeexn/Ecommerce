package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.ColorDTO;
import com.prod.models.carts.Color;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IColorFacade {
    ResponseObject<Color> createColor(ColorDTO colorDTO);
    ResponseObject<Color> updateColor(ColorDTO colorDTO);
    ResponseObject<Color> getColorById(int id);
    ResponseObject<List<Color>> getColorsByValueLike(String value);
    ResponseObject<List<Color>> getAllColors();
}
