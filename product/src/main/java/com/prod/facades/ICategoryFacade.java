package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.CategoryDTO;
import com.prod.models.details.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface ICategoryFacade {
    ResponseObject<Category> createCategory(CategoryDTO categoryDTO);
    ResponseObject<Category> updateCategory(CategoryDTO categoryDTO);
    ResponseObject<Boolean> deleteCategory(CategoryDTO categoryDTO);
    ResponseObject<Category> getCategoryById(int id);
    ResponseObject<Page<Category>> getAllCategories(int page, int limit, String sortField, String sortDirect);
    ResponseObject<Page<Category>> getCategoriesByName(String name,int page, int limit, String sortField, String sortDirect);
}
