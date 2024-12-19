package com.prod.services.carts.impl;

import com.prod.JPARepositories.carts.SizeRepository;
import com.prod.models.carts.Size;
import com.prod.services.carts.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.carts.SizeRepository.Specs.byValue;

@Service
public class SizeService implements ISizeService {
    @Autowired
    private SizeRepository sizeRepository;
    @Override
    public Size createSize(Size size) {
        if (size.getCreate_at() != size.getUpdate_at()) {
            size.setUpdate_at(LocalDateTime.now());
        }
        return sizeRepository.save(size);
    }

    @Override
    public List<Size> createSizes(List<Size> sizes) {
        List<Size> savedSizes = new ArrayList<>();
        for (Size size : sizes) {
            savedSizes.add(sizeRepository.save(size));
        }
        return savedSizes;
    }

    @Override
    public Optional<Size> getSizeById(int id) {
        return sizeRepository.findById(id);
    }

    @Override
    public Optional<Size> getSizeByValue(String value) {
        return sizeRepository.findOne(byValue(value));
    }

    @Override
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public void deleteSizeById(int id) {
        sizeRepository.deleteById(id);
    }

    @Override
    public void deleteSize(Size size) {
        sizeRepository.delete(size);
    }
}
