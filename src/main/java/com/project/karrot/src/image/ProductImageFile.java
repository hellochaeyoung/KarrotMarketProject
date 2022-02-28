package com.project.karrot.src.image;

import com.project.karrot.src.product.Product;

import javax.persistence.*;

@Entity
@DiscriminatorValue("productImageFile")
public class ProductImageFile extends ImageFiles {

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        product.getProductImages().add(this); // 해당 상품의 이미지목록에 추가
    }
}

