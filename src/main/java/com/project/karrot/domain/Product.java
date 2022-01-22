package com.project.karrot.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    private String productName;
    private int price;
    private int likeCount;
    private String time;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "product")
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    List<ProductImageFile> productImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToOne(mappedBy = "product")
    private Deal deal;

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        if(this.productStatus != null && this.productStatus.equals(ProductStatus.COMPLETE)) { // 거래완료에서
            if(productStatus.equals(ProductStatus.SALE) || productStatus.equals(ProductStatus.RESERVATION)) { // 예약중 or 판매중으로 변경 시
                this.deal.setProduct(null); // 해당 상품 거래 연관관계 삭제
            }
        }

        this.productStatus = productStatus;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<ProductImageFile> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImageFile> productImages) {
        this.productImages = productImages;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int like) {
        this.likeCount = likeCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        // 등록된 상품의 등록자가 바뀔 일은 없음.
        // 초기 등록자 설정만 필요
        this.member = member;
        this.member.getProducts().add(this);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if(this.category != null) {
            this.category.getProducts().remove(this); // 예전 카테고리의 상품목록에서 제거
        }
        this.category = category; // 새롭게 설정될 카테고리 설정
        category.getProducts().add(this);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if(this.location != null) {
            this.location.getProducts().remove(this);
        }
        this.location = location; // 등록자의 지역으로 상품 지역 설정
        this.location.getProducts().add(this);
    }
}

