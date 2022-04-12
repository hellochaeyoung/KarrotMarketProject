package com.project.karrot.src.productimage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductImageDto {

    private Long id;
    private String fileURL;

    public ProductImageDto(Long id, String fileURL) {
        this.id = id;
        this.fileURL = fileURL;
    }

    public ProductImageDto(String fileURL) {
        this.fileURL = fileURL;
    }

}
