package com.project.karrot.dto;

import com.project.karrot.domain.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private final Long productId;
    private final String productName;
    private final int price;
    private final int likeCount;
    private final String contents;

    public ProductResponseDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.likeCount = product.getLikeCount();
        this.contents = product.getContents();
    }
}
