package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.LabelDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.products.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductFacade {
    //create
    ResponseObject<ProductDTO> createProduct(ProductDTO product);

    //update
    ResponseObject<ProductDTO> updateProduct(ProductDTO productDTO);

    //read
    ResponseObject<Page<ProductDTO>> getProducts(int page, int size, int category, int season, List<Integer> label, String sortField, String sortDirection);

    ResponseObject<Page<ProductDTO>> getProductsByLabelId(int id, int page, int size, String sortField, String sortDirect);

    ResponseObject<Page<ProductDTO>> getProductsBySeasonId(int id, int page, int size, String sortField, String sortDirect);

    ResponseObject<ProductDTO> getProductById(int id);

    ResponseObject<Page<ProductDTO>> findProduct(String key, int category, int season, List<Integer> labels, int page, int size, String sortField, String sortDirection);

    ResponseObject<Page<ProductDTO>> getProductsByCategoryId(int id, int page, int size, String sortField, String sortDirect);
}
