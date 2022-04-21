package com.project.karrot.src.productimage.dto;

import com.project.karrot.src.product.Product;
import com.project.karrot.src.productimage.ProductImage;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequestDto {

    @NotBlank
    private Long productId;

    private List<String> fileUrlList;

    public ProductImage toEntity(Product product, String fileURL) {
        return ProductImage.builder()
                .fileURL(fileURL)
                .product(product)
                .build();
    }

}
