package com.project.karrot.src.product.dto;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private final Long productId;
    private final String productName;
    private final int price;
    private final int likeCount;
    private final String contents;
    private final Long memberId;

    public ProductResponseDto(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.likeCount = product.getLikeCount();
        this.contents = product.getContents();
        this.memberId = product.getMember().getId();
    }
}
