package com.prod.services.carts.impl;

import com.prod.JPARepositories.carts.ColorRepository;
import com.prod.models.carts.Color;
import com.prod.services.carts.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.carts.ColorRepository.Specs.*;

@Service
public class ColorService implements IColorService {
    @Autowired
    private ColorRepository colorRepository;
    @Override
    public Color createColor(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public List<Color> createColors(List<Color> colors) {
        List<Color> newColors = new ArrayList<>();
        for (Color color : colors) {
            newColors.add(createColor(color));
        }
        return newColors;
    }

    @Override
    public Optional<Color> getColorById(int id) {
        return colorRepository.findById(id);
    }

    @Override
    public Optional<Color> getColorByCode(String code) {
        return colorRepository.findOne(byCode(code));
    }

    @Override
    public List<Color> getColorsNameLike(String pattern) {
        return colorRepository.findAll(byValue(pattern));
    }

    @Override
    public List<Color> getColors() {
        return colorRepository.findAll();
    }

    @Override
    public void deleteColorById(int id) {
        colorRepository.deleteById(id);
    }

    @Override
    public void deleteColor(Color color) {
        colorRepository.delete(color);
    }
}
