package com.prod.services.carts;

import com.prod.models.carts.Color;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IColorService {
    //create
    Color createColor(Color color);
    List<Color> createColors(List<Color> colors);
    //get
    Optional<Color> getColorById(int id);
    Optional<Color> getColorByCode(String code);
    List<Color> getColorsNameLike(String pattern);
    List<Color> getColors();
    //delete
    void deleteColorById(int id);
    void deleteColor(Color color);
}
