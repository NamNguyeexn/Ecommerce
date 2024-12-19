package com.prod.services.details.impl;

import com.prod.JPARepositories.details.CategoryRepository;
import com.prod.models.details.Category;
import com.prod.services.ServicePage;
import com.prod.services.details.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.prod.JPARepositories.details.CategoryRepository.Specs.*;
import static com.prod.JPARepositories.details.DetailRepository.Specs.byCategoryId;

@Service
public class CategoryService extends ServicePage<Category> implements ICategoryService {
    @Autowired
    private CategoryRepository repository;
    @Override
    public Category createCategory(Category category) {
        if (category.getCreate_at() != category.getUpdate_at()) {
            category.setUpdate_at(LocalDateTime.now());
        }
        return repository.save(category);
    }

    @Override
    public List<Category> createCategories(List<Category> categories) {
        List<Category> categoryList = new ArrayList<>();
        for (Category category : categories) {
            categoryList.add(createCategory(category));
        }
        return categoryList;
    }

    @Override
    public Optional<Category> getCategoryById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> getCategoriesByName(String name) {
        List<Category> nameList = repository.findAll();
        List<Category> categoryList = new ArrayList<>();
        for (Category category : nameList) {
            if (category.getName().contains(name)) {
                categoryList.add(category);
            }
        }
        return categoryList;
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Page<Category> getPageCategories(int page, int size, String sortField, String sortDirection) {
        Pageable pageable = getPage(page, size, sortDirection, sortField);
        return repository.findAll(pageable);
    }

    @Override
    public Page<Category> getPageCategoriesByName(String name, int page, int size, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirection), sortField);
        if (Objects.equals(name, "")) return repository.findAll(pageable);
        return repository.findAll(byNameLike(name), pageable);}

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return repository.findOne(byName(name));
    }

    @Override
    public Optional<Category> getCategoryByCode(String code) {
        return repository.findOne(byCode(code));
    }

    @Override
    public void deleteCategoryById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteCategory(Category category) {
        repository.delete(category);
    }
}
