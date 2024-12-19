package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.CategoryDTO;
import com.prod.facades.ICategoryFacade;
import com.prod.models.details.Category;
import com.prod.services.details.ICategoryService;
import com.prod.utils.ConvertListPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryFacade extends Facade<Category> implements ICategoryFacade {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ConvertListPage<Category> convertListPage;

    @Override
    public ResponseObject<Category> createCategory(CategoryDTO categoryDTO) {
        try {
            Optional<Category> _category = categoryService.getCategoryByName(categoryDTO.getName());
            if (_category.isPresent()) {
                return ResponseObject.<Category>builder()
                        .data(_category.get())
                        .message("Category da ton tai")
                        .isSuccess(false)
                        .build();
            } else {
                Category category = Category.builder()
                        .name(categoryDTO.getName())
                        .build();
                return ResponseObject.<Category>builder()
                        .data(categoryService.createCategory(category))
                        .message("Tao category thanh cong")
                        .isSuccess(true)
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverError();
        }
    }

    @Override
    public ResponseObject<Category> updateCategory(CategoryDTO categoryDTO) {
        try {
            Optional<Category> _category = categoryService.getCategoryById(categoryDTO.getId());
            if (_category.isPresent()) {
                Category category = _category.get();
                category.setUpdate_at(LocalDateTime.now());
                category.setName(categoryDTO.getName());
                category.set_active(categoryDTO.isActive());
                return ResponseObject.<Category>builder()
                        .data(categoryService.createCategory(category))
                        .isSuccess(true)
                        .message("Cap nhat category thanh cong")
                        .build();
            } else return ResponseObject.<Category>builder()
                    .message("Khong tim thay category")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverError();
        }
    }

    @Override
    public ResponseObject<Boolean> deleteCategory(CategoryDTO categoryDTO) {
        try {
            Optional<Category> _category = categoryService.getCategoryById(
                    categoryDTO.getId()
            );
            if (_category.isPresent()) {
                categoryService.deleteCategory(_category.get());
                boolean res = categoryService.getCategoryById(categoryDTO.getId()).isPresent();
                return ResponseObject.<Boolean>builder()
                        .data(res)
                        .isSuccess(res)
                        .message("Da thuc hien xoa category")
                        .build();
            } else {
                return ResponseObject.<Boolean>builder()
                        .message("Khong tim thay category")
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseObject.<Boolean>builder()
                    .message("Gap loi server")
                    .build();
        }
    }

    @Override
    public ResponseObject<Category> getCategoryById(int id) {
        try {
            Optional<Category> _category = categoryService.getCategoryById(id);
            if (_category.isEmpty()) {
                return ResponseObject.<Category>builder()
                        .message("Khong the tim thay category")
                        .build();
            } else {
                return ResponseObject.<Category>builder()
                        .data(_category.get())
                        .message("Tim thay category")
                        .isSuccess(true)
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverError();
        }
    }

    @Override
    public ResponseObject<Page<Category>> getAllCategories(int page, int limit, String sortField, String sortDirect) {
        try {
            List<Category> categories = categoryService.getPageCategories(page, limit, sortField, sortDirect).getContent();
            if (categories.isEmpty()) {
                return ResponseObject.<Page<Category>>builder()
                        .message("Danh sach category trong")
                        .build();
            } else {
                return ResponseObject.<Page<Category>>builder()
                        .data(convertListPage.listToPage(categories, page, limit))
                        .message("Lay danh sach category thanh cong")
                        .isSuccess(true)
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseObject.<Page<Category>>builder()
                    .message(SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseObject<Page<Category>> getCategoriesByName(String name, int page, int limit, String sortField, String sortDirect) {
        try {
            List<Category> categories = categoryService.getPageCategoriesByName(name, page, limit, sortField, sortDirect).getContent();
            if (categories.isEmpty()) {
                return ResponseObject.<Page<Category>>builder()
                        .message("Danh sach category trong")
                        .build();
            } else {
                return ResponseObject.<Page<Category>>builder()
                        .data(convertListPage.listToPage(categories, page, limit))
                        .message("Lay danh sach category thanh cong")
                        .isSuccess(true)
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseObject.<Page<Category>>builder()
                    .message(SERVER_ERROR)
                    .build();
        }
    }
}
