package com.project.karrot.src.interest.dto;

import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterestedRequestDto {

    private Long interestedId;
    private Long memberId;
    private Long productId;
    private boolean like;

    public InterestedProduct toEntity(Member member, Product product) {
        return InterestedProduct.builder()
                .member(member)
                .product(product)
                .build();
    }
}
