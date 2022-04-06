package com.project.karrot.src.deal.dto;

import com.project.karrot.src.deal.Deal;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealResponseDto {

    private Long dealId;
    private Long productId;

    public DealResponseDto(Deal deal) {
        this.dealId = deal.getId();
        this.productId = deal.getProduct().getId();
    }
}
