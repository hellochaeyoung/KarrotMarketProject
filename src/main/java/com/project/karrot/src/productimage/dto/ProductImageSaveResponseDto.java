package com.project.karrot.src.productimage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductImageSaveResponseDto {

    private Long id;
    private String fileURL;

    public ProductImageSaveResponseDto(String fileURL) {
        this.fileURL = fileURL;
    }

}
