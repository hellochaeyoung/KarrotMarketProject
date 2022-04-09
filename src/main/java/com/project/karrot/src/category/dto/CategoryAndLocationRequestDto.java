package com.project.karrot.src.category.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAndLocationRequestDto {

    private Long categoryId;
    private Long locationId;

}
