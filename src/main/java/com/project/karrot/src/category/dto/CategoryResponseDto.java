package com.project.karrot.src.category.dto;

import com.project.karrot.src.category.Category;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private Long categoryId;
    private String categoryName;

    public CategoryResponseDto(Category category) {
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
    }
}
