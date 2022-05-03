package com.project.karrot.src.product.dto;

import com.project.karrot.src.productimage.dto.ProductImageSaveResponseDto;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 등록된 상품 내용 수정 요청 DTO
 */

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long productId;

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

    private List<ProductImageSaveResponseDto> removeImageList;
    private List<String> fileUrlList;

    public void toReady(List<String> fileUrlList) {
        this.fileUrlList = fileUrlList;
    }

}
