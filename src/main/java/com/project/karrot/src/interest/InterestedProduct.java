package com.project.karrot.src.interest;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InterestedProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long interestedId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public long getInterestedId() {
        return interestedId;
    }

    public void setInterestedId(long interestedId) {
        this.interestedId = interestedId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if(product == null) {
            this.member.getInterestedProducts().remove(this); /// 추후 수정 필요
        }else {
            // 상품을 변경할 일은 없기 때문에 this.product != null 처리 안해줘도 될듯?
            this.member.getInterestedProducts().add(this); /// 추후 수정 필요
        }
    }
}
