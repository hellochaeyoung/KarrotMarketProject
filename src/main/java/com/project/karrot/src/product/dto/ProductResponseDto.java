package com.project.karrot.src.product.dto;

import com.project.karrot.src.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;
    private String productName;
    private int price;
    private int likeCount;
    private String contents;
    private Long memberId;

    public ProductResponseDto(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.likeCount = product.getLikeCount();
        this.contents = product.getContents();
        this.memberId = product.getMember().getId();
    }
}
