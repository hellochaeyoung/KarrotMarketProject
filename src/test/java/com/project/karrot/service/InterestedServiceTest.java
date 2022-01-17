package com.project.karrot.service;

import com.project.karrot.domain.InterestedProduct;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.repository.InterestedRepository;
import com.project.karrot.repository.MemberRepository;
import com.project.karrot.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
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
class InterestedServiceTest {

    @Autowired Member member;

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired ProductService productService;
    @Autowired ProductRepository productRepository;

    @Autowired InterestedService interestedService;
    @Autowired InterestedRepository interestedRepository;

    @Test
    public void 관심상품등록() {
        //Member member = new Member();
        member.setNickName("hi");

        Product product = new Product();
        product.setProductName("p1");
        product.setMember(member);

        Long saveId = memberService.join(member);
        Product p = productService.register(product);
        InterestedProduct ip = interestedService.add(product);

        Member member1 = memberService.find(saveId).get();
        List<InterestedProduct> result = interestedService.findInterestedByMember(member1).orElseGet(() -> new ArrayList<InterestedProduct>());
        List<InterestedProduct> results = interestedService.findInterestedByProduct(p).orElseGet(() -> new ArrayList<InterestedProduct>());
        assertThat(result.get(0).getMember().getId()).isEqualTo(saveId);
        assertThat(results.get(0).getMember().getProducts().get(0).getProductId()).isEqualTo(p.getProductId());

    }


}
