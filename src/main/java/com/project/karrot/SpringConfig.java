package com.project.karrot;

import com.project.karrot.domain.ImageFiles;
import com.project.karrot.domain.Member;
import com.project.karrot.repository.*;
import com.project.karrot.service.*;
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
    private CategoryRepository categoryRepository;
    private CommentRepository commentRepository;
    private DealRepository dealRepository;
    private ImageFileRepository imageFileRepository;
    private LocationRepository locationRepository;

    public SpringConfig(EntityManager em, MemberRepository memberRepository, ProductRepository productRepository, InterestedRepository interestedRepository, CategoryRepository categoryRepository, CommentRepository commentRepository, DealRepository dealRepository, ImageFileRepository imageFileRepository, LocationRepository locationRepository) {
        this.em = em;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.interestedRepository = interestedRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.dealRepository = dealRepository;
        this.imageFileRepository = imageFileRepository;
        this.locationRepository = locationRepository;
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

    @Bean
    public CategoryService categoryService() { return new CategoryService(categoryRepository); }

    @Bean
    public CommentService commentService() { return new CommentService(commentRepository); }

    @Bean
    public DealService dealService() { return new DealService(dealRepository); }

    @Bean
    public LocationService locationService() { return new LocationService(locationRepository); }

    @Bean
    public ImageFileService imageFileService() { return new ImageFileService(imageFileRepository); }

}

