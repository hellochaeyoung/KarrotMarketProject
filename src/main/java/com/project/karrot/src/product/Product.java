package com.project.karrot.src.product;

import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.comment.Comment;
import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.image.ProductImageFile;
import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.productimage.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productName;
    private int price;
    private int likeCount;
    private String time;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "product")
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    List<ProductImage> productImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
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

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Long getId() {
        return id;
    }

    public void setProductId(Long id) {
        this.id = id;
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
        this.likeCount = like;
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

