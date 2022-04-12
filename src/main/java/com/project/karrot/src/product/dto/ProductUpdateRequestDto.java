package com.project.karrot.src.product.dto;

import com.project.karrot.src.image.dto.ImageUpdateRequestDto;
import com.project.karrot.src.productimage.dto.ProductImageDto;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {

    private Long memberId;
    private Long productId;
    private String productName;
    private String categoryName;
    private int price;
    private String contents;
    private String status;

    private List<ProductImageDto> removeImageList;
    private List<String> fileUrlList;

    public void toReady(List<String> fileUrlList) {
        this.fileUrlList = fileUrlList;
    }

}
