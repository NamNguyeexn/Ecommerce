package com.prod.services.details;

import com.prod.models.details.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICategoryService {
    //create
    Category createCategory(Category category);
    List<Category> createCategories(List<Category> categories);
    //get
    Optional<Category> getCategoryById(int id);
    List<Category> getCategoriesByName(String name);
    List<Category> getAllCategories();
    Page<Category> getPageCategories(int page, int size, String sortField, String sortDirection);
    Page<Category> getPageCategoriesByName(String name, int page, int size, String sortField, String sortDirection);
    Optional<Category> getCategoryByName(String name);
    Optional<Category> getCategoryByCode(String code);
    //delete
    void deleteCategoryById(int id);
    void deleteCategory(Category category);
}
