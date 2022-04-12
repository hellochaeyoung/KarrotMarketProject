package com.project.karrot.src.product.dto;

import com.project.karrot.src.productimage.dto.ProductImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
public class ProductAndImageResponseDto {

    private final ProductResponseDto productResponseDto;
    private final List<ProductImageDto> imageFileList;

    public ProductAndImageResponseDto(ProductResponseDto productResponseDto, List<ProductImageDto> imageFileList) {
        this.productResponseDto = productResponseDto;
        this.imageFileList = imageFileList;
    }
}
