package com.project.karrot.common.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class SpringConfig {

    /*
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final InterestedRepository interestedRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final LocationRepository locationRepository;
    private final MemberImageRepository memberImageRepository;
    private final ProductImageRepository productImageRepository;

    private final UploadService uploadService;


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository, locationRepository, memberImageRepository);
    }

    @Bean
    public MemberAuthService memberAuthService() {
        return new MemberAuthService(memberRepository);
    }

    @Bean
    public ProductService productService() {return new ProductService(productRepository, categoryRepository, memberRepository, productImageRepository, dealRepository); }

    @Bean
    public InterestedService interestedService() { return new InterestedService(interestedRepository, memberRepository, productRepository); }

    @Bean
    public CategoryService categoryService() { return new CategoryService(categoryRepository); }

    @Bean
    public CommentService commentService() { return new CommentService(commentRepository, memberRepository, productRepository); }

    @Bean
    public DealService dealService() { return new DealService(dealRepository, memberRepository, productRepository); }

    @Bean
    public LocationService locationService() { return new LocationService(locationRepository); }

    @Bean
    public FileUploadService fileUploadService() { return new FileUploadService(uploadService); }

    @Bean
    public MemberImageService memberImageService() { return new MemberImageService(memberImageRepository, memberRepository); }

    @Bean
    public ProductImageService productImageService() { return new ProductImageService(productImageRepository); }
*/
}

