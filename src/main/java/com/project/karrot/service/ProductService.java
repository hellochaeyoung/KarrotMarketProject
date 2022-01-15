package com.project.karrot.service;

import com.project.karrot.domain.*;
import com.project.karrot.repository.ProductRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product register(Product product) {

        // 추후 이 set 함수들은 앞단에서 처리
        product.setProductStatus(ProductStatus.SALE); // 진행단계 설정, 저장
        product.setTime(fomatDate()); // 게시시간 저장

        return productRepository.save(product);

    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }

    public Optional<Product> find(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findByLocation(Location location) {
        return productRepository.findByLocation(location);
    }

    public List<Product> findByLocationAndCategory(Location location, Category category) {
        return productRepository.findByLocationAndCategory(location, category);
    }

    public List<Product> findByMember(Member member) {
        return productRepository.findByMember(member);
    }

    public List<Product> findByMemberAndStatus(Member member, ProductStatus status) {
        return productRepository.findByMemberAndStatus(member, status);
    }

    public void remove(Product product) {
        productRepository.delete(product);
    }

}

