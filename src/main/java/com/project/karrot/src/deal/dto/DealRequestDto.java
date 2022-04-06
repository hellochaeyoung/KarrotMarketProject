package com.project.karrot.src.deal.dto;

import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealRequestDto {

    private Long memberId;
    private Long productId;

    public Deal toEntity(Member member, Product product) {
        return Deal.builder()
                .member(member)
                .product(product)
                .build();
    }

}
