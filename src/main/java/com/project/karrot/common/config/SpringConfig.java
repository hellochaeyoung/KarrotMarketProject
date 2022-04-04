package com.project.karrot.common.config;

import com.project.karrot.src.aop.LoginCheckAspect;
import com.project.karrot.src.category.CategoryRepository;
import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.comment.CommentRepository;
import com.project.karrot.src.comment.CommentService;
import com.project.karrot.src.deal.DealRepository;
import com.project.karrot.src.deal.DealService;
import com.project.karrot.src.image.ImageFileRepository;
import com.project.karrot.src.image.ImageFileService;
import com.project.karrot.src.interest.InterestedRepository;
import com.project.karrot.src.interest.InterestedService;
import com.project.karrot.src.location.LocationRepository;
import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.member.*;
import com.project.karrot.src.product.ProductRepository;
import com.project.karrot.src.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class SpringConfig {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final InterestedRepository interestedRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final ImageFileRepository imageFileRepository;
    private final LocationRepository locationRepository;

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository, locationRepository);
    }

    @Bean
    public MemberAuthService memberAuthService() {
        return new MemberAuthService(memberRepository);
    }

    @Bean
    public ProductService productService() {return new ProductService(productRepository, categoryRepository, memberRepository, locationRepository); }

    @Bean
    public InterestedService interestedService() { return new InterestedService(interestedRepository); }

    @Bean
    public CategoryService categoryService() { return new CategoryService(categoryRepository); }

    @Bean
    public CommentService commentService() { return new CommentService(commentRepository, memberRepository, productRepository); }

    @Bean
    public DealService dealService() { return new DealService(dealRepository); }

    @Bean
    public LocationService locationService() { return new LocationService(locationRepository); }

    @Bean
    public ImageFileService imageFileService() { return new ImageFileService(imageFileRepository); }

}

