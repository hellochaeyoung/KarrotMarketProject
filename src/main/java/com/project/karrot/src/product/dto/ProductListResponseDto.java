package com.project.karrot.src.product.dto;

import com.project.karrot.src.product.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductListResponseDto {

    private List<ProductResponseDto> productList;

    public List<ProductResponseDto> EntityListToDtoList(List<Product> productList) {
        this.productList = productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());

        return this.productList;
    }
}
