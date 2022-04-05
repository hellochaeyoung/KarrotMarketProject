package com.project.karrot.src.product;

import com.project.karrot.src.category.Category;
import com.project.karrot.src.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findByLocation(Long locationId);

    Optional<List<Product>> findByLocationIdAndCategoryId(Long locationId, Long categoryId); // 지역별 및 카테고리별 상품 조회

    Optional<List<Product>> findByMemberId(Long memberId); // 회원 등록 상품 전체 조회

    Optional<List<Product>> findByMemberAndProductStatus(Long memberId, String productStatus); // 회원별 진행단계별 조회

}

