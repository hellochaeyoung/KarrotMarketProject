package com.project.karrot.service;

import com.project.karrot.domain.*;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.ProductListResponseDto;
import com.project.karrot.dto.ProductRequestDto;
import com.project.karrot.dto.ProductResponseDto;
import com.project.karrot.repository.CategoryRepository;
import com.project.karrot.repository.MemberRepository;
import com.project.karrot.repository.ProductRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
    }

    public Product register(ProductRequestDto productRequestDto, MemberRequestDto memberRequestDto) {

        Member member = memberRepository.findByNickName(memberRequestDto.getNickName()).orElseThrow();
        Category findCategory = categoryRepository.findByCategoryName(productRequestDto.getCategoryName()).orElseThrow();
        String time = fomatDate();

        productRequestDto.setProductRequestDto(member, findCategory, time);
        Product newProduct = productRequestDto.toEntity();

        Product result = productRepository.save(newProduct);

        productRepository.flush();

        return result;

    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }

    public ProductResponseDto findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> findByLocation(Location location) {
        List<Product> productList = productRepository.findByLocationId(location.getLocationId()).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findByLocationAndCategory(Location location, Category category) {
        List<Product> productList = productRepository.findByLocationIdAndCategoryId(location.getLocationId(), category.getCategoryId()).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findByMember(Long memberId) {
        List<Product> productList = productRepository.findByMemberId(memberId).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findByMemberAndStatus(Long memberId, ProductStatus status) {
        List<Product> productList = productRepository.findByMemberIdAndProductStatus(memberId, status.name()).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public void remove(Product product) {
        productRepository.delete(product);
    }

}

