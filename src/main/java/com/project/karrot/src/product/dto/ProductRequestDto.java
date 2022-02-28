package com.project.karrot.src.product.dto;

import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    // 이미지 변수 추가

    private Long memberId;
    private Long productId;
    private String productName;
    private String categoryName;
    private int price;
    private String contents;
    private String status;

    private Member member;
    private Category category;
    private String time;
    private Location location;
    private ProductStatus productStatus;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .price(price)
                .contents(contents)
                .member(member)
                .category(category)
                .time(time)
                .location(location)
                .productStatus(productStatus)
                .build();
    }

    public void setProductRequestDto(Member member, Category category, String time) {
        this.member = member;
        this.category = category;
        this.time = time;
        this.location = member.getLocation();
        this.productStatus = ProductStatus.SALE;
    }
}