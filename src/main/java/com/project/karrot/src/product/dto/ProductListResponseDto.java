package com.project.karrot.src.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponseDto {

    private ProductResponseDto selectProduct;
    private List<ProductResponseDto> otherProductList;
}
