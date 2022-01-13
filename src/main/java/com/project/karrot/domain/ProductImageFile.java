package com.project.karrot.domain;

import javax.persistence.*;

@Entity
public class ProductImageFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    private String fileName;
    private String fileOriName;
    private String fileURL;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOriName() {
        return fileOriName;
    }

    public void setFileOriName(String fileOriName) {
        this.fileOriName = fileOriName;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        product.getProductImages().add(this); // 해당 상품의 이미지목록에 추가
    }
}

