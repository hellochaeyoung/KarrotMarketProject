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

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final InterestedRepository interestedRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final ImageFileRepository imageFileRepository;
    private final LocationRepository locationRepository;

    public SpringConfig(MemberRepository memberRepository, ProductRepository productRepository, InterestedRepository interestedRepository, CategoryRepository categoryRepository, CommentRepository commentRepository, DealRepository dealRepository, ImageFileRepository imageFileRepository, LocationRepository locationRepository) {
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
    public Member member() { return new Member(); }


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    @Bean
    public ProductService productService() {return new ProductService(member(), productRepository); }

    @Bean
    public InterestedService interestedService() { return new InterestedService(interestedRepository); }

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

