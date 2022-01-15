package com.project.karrot;

import com.project.karrot.domain.Member;
import com.project.karrot.repository.*;
import com.project.karrot.service.InterestedService;
import com.project.karrot.service.MemberService;
import com.project.karrot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private EntityManager em;
    private MemberRepository memberRepository;
    private ProductRepository productRepository;
    private InterestedRepository interestedRepository;

    public SpringConfig(EntityManager em, MemberRepository memberRepository, ProductRepository productRepository, InterestedRepository interestedRepository) {
        this.em = em;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.interestedRepository = interestedRepository;
    }

    @Bean
    public Member member() { return new Member(); } /////////////////////////

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
/*
    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        return new JPAMemberRepository(em);
    }
*/
    @Bean
    public ProductService productService() {return new ProductService(productRepository); }
/*
    @Bean
    public ProductRepository productRepository() {
        return new JPAProductRepository(em);
    }
*/
    @Bean
    public InterestedService interestedService() { return new InterestedService(member(), interestedRepository); }


}

