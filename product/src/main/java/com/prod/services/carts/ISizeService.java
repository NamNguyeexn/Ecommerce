package com.prod.services.carts;

import com.prod.models.carts.Size;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISizeService {
    //create
    Size createSize(Size size);
    List<Size> createSizes(List<Size> sizes);
    //get
    Optional<Size> getSizeById(int id);
    Optional<Size> getSizeByValue(String value);
    List<Size> getAllSizes();
    //delete
    void deleteSizeById(int id);
    void deleteSize(Size size);
}
