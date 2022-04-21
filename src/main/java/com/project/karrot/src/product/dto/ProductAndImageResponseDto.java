package com.project.karrot.src.product.dto;

import com.project.karrot.src.productimage.dto.ProductImageSaveResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndImageResponseDto {

    private ProductResponseDto productResponseDto;
    private List<ProductImageSaveResponseDto> imageFileList;

}
