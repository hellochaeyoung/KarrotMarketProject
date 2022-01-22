package com.project.karrot.service;

import com.project.karrot.domain.Category;
import com.project.karrot.repository.CategoryRepository;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category register(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Long remove(Category category) {
        categoryRepository.delete(category);

        return category.getCategoryId();
    }
}
