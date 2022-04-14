package com.project.karrot.src.interest.dto;

import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.product.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterestedResponseDto {

    private Long interestedId;
    private ProductResponseDto productResponseDto;

    public InterestedResponseDto(InterestedProduct interestedProduct) {
        this.interestedId = interestedProduct.getId();
        productResponseDto = new ProductResponseDto(interestedProduct.getProduct());
    }
}
