package com.project.karrot.repository;

import com.project.karrot.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findByLocationId(Long locationId);

    Optional<List<Product>> findByLocationIdAndCategoryId(Long locationId, Long categoryId); // 지역별 및 카테고리별 상품 조회

    Optional<List<Product>> findByMemberId(Long memberId); // 회원 등록 상품 전체 조회

    Optional<List<Product>> findByMemberIdAndProductStatus(Long memberId, String productStatus); // 회원별 진행단계별 조회


}

