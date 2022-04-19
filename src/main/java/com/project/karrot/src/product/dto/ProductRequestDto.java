package com.project.karrot.src.product.dto;

import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.*;

import java.util.List;

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
    private Integer price;
    private String contents;
    private String status;

    private List<String> fileUrlList;
    private ProductStatus productStatus;

    public Product toEntity(Member member, Category category, String time) {
        return Product.builder()
                .id(productId)
                .productName(productName)
                .price(price)
                .contents(contents)
                .member(member)
                .category(category)
                .time(time)
                .location(member.getLocation())
                .productStatus(ProductStatus.SALE)
                .build();
    }

    public void update(ProductStatus status) {
        this.productStatus = status;
    }

    public void toReady(Long memberId, List<String> fileUrlList) {
        this.memberId = memberId;
        this.fileUrlList = fileUrlList;
    }
}
