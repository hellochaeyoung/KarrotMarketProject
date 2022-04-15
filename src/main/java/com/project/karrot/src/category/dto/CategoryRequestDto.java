package com.project.karrot.src.category.dto;

import com.project.karrot.src.category.Category;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    private Long categoryId;
    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }

}
