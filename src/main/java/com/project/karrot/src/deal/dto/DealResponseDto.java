package com.project.karrot.src.deal.dto;

import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.product.dto.ProductResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealResponseDto {

    private Long dealId;
    private ProductResponseDto productResponseDto;

    public DealResponseDto(Deal deal) {
        this.dealId = deal.getId();
        productResponseDto = new ProductResponseDto(deal.getProduct());
    }

}
