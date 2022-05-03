package com.project.karrot.src.product.dto;

import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 상품 등록 요청 DTO
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    private Long memberId;

    @NotBlank
    private String productName;

    @NotBlank
    private String categoryName;

    @NotNull
    @Range(min = 100, max = 2000000)
    private Integer price;

    @NotBlank
    @Size(min = 5)
    private String contents;

    private List<String> fileUrlList;
    private ProductStatus productStatus;

    public Product toEntity(Member member, Category category, String time) {
        return Product.builder()
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
