package com.project.karrot.service;

import com.project.karrot.domain.Category;
import com.project.karrot.dto.CategoryRequestDto;
import com.project.karrot.dto.CategoryResponseDto;
import com.project.karrot.repository.CategoryRepository;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto register(CategoryRequestDto categoryRequest) {

        Category category = categoryRepository.save(categoryRequest.toEntity());

        return new CategoryResponseDto(category);
    }

    public CategoryResponseDto findByName(String name) {
        Category category = categoryRepository.findByCategoryName(name).orElseThrow();

        return new CategoryResponseDto(category);
    }

    public List<CategoryResponseDto> findAll() {
        List<Category> list = categoryRepository.findAll();

        return list.stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    public Long remove(CategoryRequestDto category) {
        categoryRepository.deleteById(category.getCategoryId());

        return category.getCategoryId();
    }
}
