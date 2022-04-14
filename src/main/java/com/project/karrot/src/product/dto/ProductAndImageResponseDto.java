package com.project.karrot.src.product.dto;

import com.project.karrot.src.productimage.dto.ProductImageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
public class ProductAndImageResponseDto {

    private ProductResponseDto productResponseDto;
    private List<ProductImageDto> imageFileList;

    public ProductAndImageResponseDto(ProductResponseDto productResponseDto, List<ProductImageDto> imageFileList) {
        this.productResponseDto = productResponseDto;
        this.imageFileList = imageFileList;
    }
}
