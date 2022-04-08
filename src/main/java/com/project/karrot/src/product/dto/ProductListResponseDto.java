package com.project.karrot.src.product.dto;

import com.project.karrot.src.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ProductListResponseDto {

    private ProductResponseDto selectProduct;
    private List<ProductResponseDto> otherProductList;
}
