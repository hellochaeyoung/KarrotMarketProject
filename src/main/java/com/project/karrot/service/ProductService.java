package com.project.karrot.service;

import com.project.karrot.domain.Product;
import com.project.karrot.domain.ProductStatus;
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

    public Long register(Product product) {

        product.setProductStatus(ProductStatus.SALE); // 진행단계 설정, 저장
        product.setTime(fomatDate()); // 게시시간 저장

        productRepository.save(product);

        return product.getProductId();
    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }

    // 상품 상세조회 - 공통
    public Optional<Product> findByProductId(Long productId) {
        return productRepository.findByProductId(productId);
    }

    /* 상품 메인 페이지 */

    // 맨 처음 로그인 후 바로 나오는 화면, 지역별 상품들 목록 조회
    public List<Product> findByLocationId(Long locationId) {
        return productRepository.findByLocationId(locationId);
    }

    // 카테고리 설정 추가 시 조회
    public List<Product> findByCategoryId(Long locationId, Long categoryId) {
        return productRepository.findByLocationAndCategory(locationId, categoryId);
    }

    /* 마이페이지 */

    // 전체 목록 조회
    public List<Product> findByMemberId(Long memberId) {
        return productRepository.findByMemberId(memberId);
    }

    // 진행단계별(판매중 or 거래완료) 목록 조회
    public List<Product> findByMemberAndStep(Long memberId, String status) {
        return productRepository.findByMemberAndStatus(memberId, status);
    }


}

