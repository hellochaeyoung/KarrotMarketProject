package com.project.karrot.src.product.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAndStatusRequestDto {

    private ProductRequestDto productRequestDto;
    private String status;

}
