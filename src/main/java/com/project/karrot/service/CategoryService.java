package com.project.karrot.service;

import com.project.karrot.domain.Category;
import com.project.karrot.repository.CategoryRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category register(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void remove(Category category) {
        categoryRepository.delete(category);
    }
}
