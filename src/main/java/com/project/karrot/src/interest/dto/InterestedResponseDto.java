package com.project.karrot.src.interest.dto;

import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.product.dto.ProductResponseDto;
import lombok.*;

@Builder
@Getter
@Setter
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
