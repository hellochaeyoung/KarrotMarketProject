package com.project.karrot.service;

import com.project.karrot.domain.Category;
import com.project.karrot.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired CategoryService categoryService;
    @Autowired CategoryRepository categoryRepository;

    @Test
    public void 카테고리_목록_조회() {

        List<Category> results = categoryService.findAll();

        for(Category c : results) {
            System.out.println(c.getCategoryName());
        }
    }

    @Test
    public void 카테고리_추가() {
        Category category = new Category();
        category.setCategoryName("등산용품");

        Category save = categoryService.register(category);

        assertEquals(save.getCategoryName(), "등산용품");
    }

    @Test
    public void 카테고리_삭제() {

        Category category = categoryService.findByName("중고차").get();

        Long removeId = categoryService.remove(category);

        assertEquals(category.getCategoryId(), removeId);

    }


}
