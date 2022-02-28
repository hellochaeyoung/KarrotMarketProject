package com.project.karrot.service;

import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.interest.InterestedService;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.interest.InterestedRepository;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.product.ProductRepository;
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
class InterestedServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired
    ProductService productService;
    @Autowired ProductRepository productRepository;

    @Autowired
    InterestedService interestedService;
    @Autowired InterestedRepository interestedRepository;

    @Test
    public void 관심상품등록() {
        Member member = new Member();
        member.setNickName("hi");
        Long saveId = memberService.join(member);

        Product product = new Product();
        product.setProductName("p1");
        product.setMember(member);

        Product p = productService.register(product);

        Member member1 = memberService.find(saveId).get();

        InterestedProduct interestedProduct = new InterestedProduct();
        interestedProduct.setMember(member1);
        interestedProduct.setProduct(p);
        InterestedProduct ip = interestedService.add(interestedProduct);

        List<InterestedProduct> result = interestedService.findInterestedByMember(member1).orElseGet(ArrayList::new);
        List<InterestedProduct> results = interestedService.findInterestedByProduct(p).orElseGet(ArrayList::new);
        assertThat(result.get(0).getMember().getId()).isEqualTo(saveId);
        assertThat(results.get(0).getMember().getProducts().get(0).getProductId()).isEqualTo(p.getProductId());

    }


}
