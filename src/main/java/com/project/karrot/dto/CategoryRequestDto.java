package com.project.karrot.dto;

import com.project.karrot.domain.Category;
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
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }

}
