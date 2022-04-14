package com.project.karrot.src.product.dto;

import com.project.karrot.src.category.dto.CategoryResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductAndCategoryRes {

    private List<CategoryResponseDto> categoryList;
    private ProductResponseDto productRes;
    private List<ProductResponseDto> productList;

    public ProductAndCategoryRes(List<CategoryResponseDto> categoryList, ProductResponseDto productRes) {
        this.categoryList = categoryList;
        this.productRes = productRes;
    }

    public ProductAndCategoryRes(List<CategoryResponseDto> categoryList, List<ProductResponseDto> productList) {
        this.categoryList = categoryList;
        this.productList = productList;
    }
}
