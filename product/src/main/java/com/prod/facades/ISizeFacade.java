package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.SizeDTO;
import com.prod.models.carts.Size;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISizeFacade {
    ResponseObject<Size> createSize(SizeDTO sizeDTO);
    ResponseObject<Size> updateSize(SizeDTO sizeDTO);
    ResponseObject<Size> getSizeById(int id);
    ResponseObject<List<Size>> getAllSizes();
}
