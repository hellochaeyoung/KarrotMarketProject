package com.project.karrot.domain;

import javax.persistence.*;

@Entity
public class InterestedProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long interestedId;

    @ManyToOne
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
        if(product == null) {
            this.member.getInterestedProducts().remove(this); /// 추후 수정 필요
        }else {
            this.product = product;
            this.member.getInterestedProducts().add(this); /// 추후 수정 필요
        }
    }
}
