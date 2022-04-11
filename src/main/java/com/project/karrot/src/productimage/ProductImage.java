package com.project.karrot.src.productimage;

import com.project.karrot.src.product.Product;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public ProductImage(Product product, String fileURL) {
        this.product = product;
        this.fileURL = fileURL;
    }

}
