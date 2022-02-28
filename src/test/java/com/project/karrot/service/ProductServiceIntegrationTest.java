package com.project.karrot.service;

import com.project.karrot.src.interest.InterestedService;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.product.ProductRepository;
import com.project.karrot.src.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired
    ProductService productService;
    @Autowired ProductRepository productRepository;

    @Autowired
    InterestedService interestedService;

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
        Long saveId = memberService.join(member);
        Product p = productService.register(product);

        //then
        Member findMember = memberService.find(saveId).get();
        List<Product> result = productService.findByMember(findMember).orElseGet(ArrayList::new);

        System.out.println("findMemberId : " + findMember.getId());
        System.out.println("productMemberId : " + result.get(0).getMember().getId());

        assertEquals(result.get(0).getMember().getId(), findMember.getId());
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
        product.setProductStatus(ProductStatus.SALE);

        //when
        memberService.join(member);
        productService.register(product);

        //then
        List<Product> result = productService.findByMemberAndStatus(member, ProductStatus.SALE).orElseGet(ArrayList::new);
        assertThat(result.get(0).getProductStatus().name()).isEqualTo(ProductStatus.SALE.name());
    }

    @Test
    public void 회원_관심상품_조회() {
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

        InterestedProduct interestedProduct = new InterestedProduct();
        interestedProduct.setMember(member);
        interestedProduct.setProduct(product);
        interestedService.add(interestedProduct);

        //then
        Member m1 = memberService.find(member.getId()).get();
        List<InterestedProduct> result = interestedService.findInterestedByMember(m1).orElseGet(ArrayList::new);

        assertThat(result.get(0).getMember().getName()).isEqualTo("hellochaeyoung");

    }

    @Test
    public void 상품_삭제() {
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

        //Long addProductId = productService.addInterestedProduct(product, member);

        //then
        //Product p1 = productRepository.findByProductId(product.getProductId()).get();
        //Long deleteId = productService.delete(p1);

        /*
        List<Product> result = productRepository.findInterestedProduct(member.getId());
        for(Product p : result) {
            System.out.println(p.getProductId() + " " + p.getMember().getId());
        }*/ // 외래키 다 null 처리 필요..!! -> 그냥 오류 리턴..?

        //assertThat(p1.getProductId()).isEqualTo(deleteId);
    }
}
