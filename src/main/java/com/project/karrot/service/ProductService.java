package com.project.karrot.service;

import com.project.karrot.domain.*;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.ProductListResponseDto;
import com.project.karrot.dto.ProductResponseDto;
import com.project.karrot.repository.ProductRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product register(Product product) {

        Product result = productRepository.save(product);

        productRepository.flush();

        return result;

    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }

    public ProductResponseDto find(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(NullPointerException::new);

        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> findByLocation(Location location) {
        List<Product> productList = productRepository.findByLocationId(location.getLocationId()).orElseGet(ArrayList::new);

        return new ProductListResponseDto().EntityListToDtoList(productList);
    }

    public List<ProductResponseDto> findByLocationAndCategory(Location location, Category category) {
        List<Product> productList = productRepository.findByLocationIdAndCategoryId(location.getLocationId(), category.getCategoryId()).orElseGet(ArrayList::new);

        return new ProductListResponseDto().EntityListToDtoList(productList);
    }

    public List<ProductResponseDto> findByMember(Long memberId) {
        List<Product> productList = productRepository.findByMemberId(memberId).orElseGet(ArrayList::new);

        return new ProductListResponseDto().EntityListToDtoList(productList);
    }

    public List<ProductResponseDto> findByMemberAndStatus(Long memberId, ProductStatus status) {
        List<Product> productList = productRepository.findByMemberIdAndProductStatus(memberId, status.name()).orElseGet(ArrayList::new);

        return new ProductListResponseDto().EntityListToDtoList(productList);
    }

    public void remove(Product product) {
        productRepository.delete(product);
    }

}

