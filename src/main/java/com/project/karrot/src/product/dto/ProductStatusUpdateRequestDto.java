package com.project.karrot.src.product.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 상품의 등록 상태 수정 요청 DTO
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusUpdateRequestDto {

    @NotNull
    private Long productId;

    @NotBlank
    private String status;

}
