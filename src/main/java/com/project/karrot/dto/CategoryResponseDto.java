package com.project.karrot.dto;

import com.project.karrot.domain.Category;
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
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}
