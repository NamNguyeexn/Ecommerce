package com.prod.services.products.impl;

import com.prod.JPARepositories.products.ImageRepository;
import com.prod.models.ENUM.Type_Image;
import com.prod.models.products.Image;
import com.prod.services.products.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.products.ImageRepository.Specs.*;
@Service
public class ImageService implements IImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Override
    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Image> createImages(List<Image> images) {
        return imageRepository.saveAll(images);
    }

    @Override
    public Optional<Image> getImageById(int id) {
        return imageRepository.findById(id);
    }

    @Override
    public Optional<Image> getImageByProdIdAndType(int productId, Type_Image typeImage) {
        return imageRepository.findOne(byProductId(productId).and(byType(typeImage)));
    }

    @Override
    public List<Image> getImagesByType(Type_Image typeImage) {
        return imageRepository.findAll(byType(typeImage));
    }

    @Override
    public List<Image> getImagesByProductId(int productId) {
        return imageRepository.findAll(byProductId(productId));
    }

    @Override
    public Image updateImageByProductId(int id, Image image) {
        image.setUpdate_at(LocalDateTime.now());
        return imageRepository.save(image);
    }

    @Override
    public void deleteImageById(int id) {
        imageRepository.deleteById(id);
    }

    @Override
    public void deleteImageByProductId(int id) {
        imageRepository.deleteAll(getImagesByProductId(id));
    }
}
