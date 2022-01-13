package com.project.karrot.service;

import com.project.karrot.domain.*;
import com.project.karrot.repository.MemberRepository;
import com.project.karrot.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired ProductService productService;
    @Autowired ProductRepository productRepository;

    @Test
    public void 상품등록() {
        //given
        Member member = new Member();
        member.setName("hellochaeyoung");


        Product product = new Product();
        product.setProductName("휴대폰충전기");
        product.setContents("새 휴대폰 충전기입니다.");
        product.setMember(member);

        //when
        memberService.join(member);
        productService.register(product);
        product.setMember(member);

        //then
        Long memberId = memberRepository.findById(member.getId()).get().getId();
        List<Product> result = productRepository.findByMemberId(memberId);

        System.out.println("findMemberId : " + memberId);
        System.out.println("productMemberId : " + result.get(0).getMember().getId());

        assertEquals(result.get(0).getMember().getId(), memberId);
    }

    @Test
    public void 판매중_상품_조회() {
        //given
        Member member = new Member();
        member.setName("hellochaeyoung");


        Product product = new Product();
        product.setProductName("휴대폰충전기");
        product.setContents("새 휴대폰 충전기입니다.");
        product.setMember(member);

        //when
        memberService.join(member);
        productService.register(product);
        product.setMember(member);

        //then
        List<Product> result = productRepository.findByMemberAndStatus(member.getId(), ProductStatus.SALE.name());
        assertThat(result.get(0).getProductStatus().name()).isEqualTo(ProductStatus.SALE.name());
    }
}
