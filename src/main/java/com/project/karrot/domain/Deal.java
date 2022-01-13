package com.project.karrot.domain;

import javax.persistence.*;

@Entity
public class Deal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dealId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(mappedBy = "deal")
    private Product product;

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getDeals().add(this); // 만약 거래완료에서 예약중으로 바꾸면 deal 목록에서 빼야하는디 이건 더 생각해서 해결하기
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
