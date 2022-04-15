package com.project.karrot.service;

import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryRequestDto;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void 카테고리_목록_조회() {

        List<CategoryResponseDto> results = categoryService.findAll();

        assertThat(results.size()).isGreaterThan(0);
    }

    @Test
    public void 카테고리_추가() {
        CategoryRequestDto categoryRequestDto =
                CategoryRequestDto.builder().categoryName("등산용품").build();

        CategoryResponseDto save = categoryService.register(categoryRequestDto);

        assertEquals(save.getCategoryName(), "등산용품");
    }

    @Test
    public void 카테고리_삭제() {

        CategoryResponseDto categoryResponseDto = categoryService.findByName("중고차");

        Long removeId = categoryService.remove(categoryResponseDto.getCategoryId());

        assertEquals(categoryResponseDto.getCategoryId(), removeId);

    }




}
