package com.project.karrot.src.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
public class ProductAndImageResponseDto {

    private final ProductResponseDto productResponseDto;
    private final List<String> imageFileList;

    public ProductAndImageResponseDto(ProductResponseDto productResponseDto, List<String> imageFileList) {
        this.productResponseDto = productResponseDto;
        this.imageFileList = imageFileList;
    }
}
