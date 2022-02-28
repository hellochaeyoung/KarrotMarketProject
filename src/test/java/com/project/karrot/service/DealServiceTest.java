package com.project.karrot.service;

import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.deal.DealService;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class DealServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;
    @Autowired
    DealService dealService;

    @Test
    public void 거래완료_수정_테스트() {

        Member member = new Member();
        member.setName("cyahn");

        Product product = new Product();
        product.setMember(member);
        product.setProductName("가방");

        Long memberId = memberService.join(member);
        Product product1 = productService.register(product);

        product1.setProductStatus(ProductStatus.COMPLETE);
        Deal deal = new Deal();
        deal.setMember(member);
        deal.setProduct(product1);

        Deal registerDeal = dealService.register(deal);

        assertEquals(member.getId(), registerDeal.getMember().getId());

        product1.setProductStatus(ProductStatus.SALE);

        assertEquals(registerDeal.getProduct(), null);
        assertEquals(registerDeal.getMember().getDeals().size(), 0);

        Deal removeDeal = dealService.register(registerDeal);

        assertEquals(registerDeal.getDealId(), removeDeal.getDealId());

    }

    @Test
    public void 회원_거래완료_목록_조회() {

        Member member = new Member();
        member.setName("cyahn");

        Product product = new Product();
        product.setMember(member);
        product.setProductName("가방");

        Long memberId = memberService.join(member);
        Product product1 = productService.register(product);

        product1.setProductStatus(ProductStatus.COMPLETE);
        Deal deal = new Deal();
        deal.setMember(member);
        deal.setProduct(product1);

        Deal registerDeal = dealService.register(deal);

        List<Deal> result = dealService.findByMember(member).orElseGet(() -> new ArrayList<Deal>());

        assertEquals(result.get(0).getMember().getId(), memberId);
    }
}
