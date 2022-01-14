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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class InterestedServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired ProductService productService;
    @Autowired ProductRepository productRepository;

    @Autowired InterestedService interestedService;
    @Autowired InterestedRepository interestedRepository;

    @Test
    public void 관심상품등록() {
        Member member = new Member();
        member.setNickName("hi");

        Product product = new Product();
        product.setProductName("p1");
        product.setMember(member);

        InterestedProduct interestedProduct = new InterestedProduct();
        interestedProduct.setMember(member);
        interestedProduct.setProduct(product);

        memberService.join(member);
        productService.register(product);
        interestedService.add(interestedProduct);

        List<InterestedProduct> product1 = interestedRepository.findByMember(member);

        assertThat(product1.get(0).getMember()).isEqualTo(member);

    }
}
