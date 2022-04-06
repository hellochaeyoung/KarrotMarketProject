package com.project.karrot.src.deal;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setDealId(Long id) {
        this.id = id;
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
        if(product == null) { // 상품 자체 삭제 또는 상품 진행 단계 변경(거래완료 -> 예약중 or 거래완료 -> 판매중)으로 인한 연관관계 삭제
            //this.product.setDeal(null); // 상품 - 거래 연관관계 삭제
            this.member.getDeals().remove(this); // 회원 - 거래 연관관계 삭제
        }else {
            this.product.setDeal(this);
            this.member.getDeals().add(this); // 만약 거래완료에서 예약중으로 바꾸면 deal 목록에서 빼야하는디 이건 더 생각해서 해결하기
        }
    }
}
