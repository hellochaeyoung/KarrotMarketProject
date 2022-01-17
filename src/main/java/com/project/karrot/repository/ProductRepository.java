package com.project.karrot.repository;

import com.project.karrot.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(" select p from Product p where p.location = :location")
    Optional<List<Product>> findByLocation(@Param("location")Location location);

    @Query(" select p from Product p where p.location = :location and p.category = :category")
    Optional<List<Product>> findByLocationAndCategory(@Param("location") Location location, @Param("category") Category category); // 지역별 및 카테고리별 상품 조회

    @Query(" select p from Product p where p.member = :member")
    Optional<List<Product>> findByMember(@Param("member") Member member); // 회원 등록 상품 전체 조회

    @Query(" select p from Product p where p.member = :member and p.productStatus = :status")
    Optional<List<Product>> findByMemberAndStatus(@Param("member") Member member, @Param("status") ProductStatus status); // 회원별 진행단계별 조회


}

