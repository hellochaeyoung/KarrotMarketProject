package com.project.karrot;

import com.project.karrot.domain.Member;
import com.project.karrot.repository.JPAMemberRepository;
import com.project.karrot.repository.JPAProductRepository;
import com.project.karrot.repository.MemberRepository;
import com.project.karrot.repository.ProductRepository;
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

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public Member member() { return new Member(); } /////////////////////////

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        return new JPAMemberRepository(em);
    }

    @Bean
    public ProductService productService() {return new ProductService(productRepository()); }

    @Bean
    public ProductRepository productRepository() {
        return new JPAProductRepository(em);
    }

}

