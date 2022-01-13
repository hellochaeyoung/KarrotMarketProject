package com.project.karrot.repository;

import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByProductId(Long productId); // 상품 상세 조회(상품 클릭 시)
    int updateLikeCountOfProduct(Long ProductId, int count); // 상품 좋아요 수 업데이트
    List<Comment> findAllComment(Long productId); // 상품 댓글 목록 조회


    List<Product> findByLocationId(Long locationId); // 지역별 상품 조회
    List<Product> findByLocationAndCategory(Long locationId, Long categoryId); // 지역별 및 카테고리별 상품 조회

    List<Product> findByMemberId(Long memberId); // 회원 등록 상품 전체 조회
    List<Product> findByMemberAndStatus(Long memberId, String status); // 회원별 진행단계별 조회
    List<Product> findInterestedProduct(Long memberId); // 회원 관심상품 목록 전체 조회
    List<Product> findDealProduct(Long memberId); // 회원 구매상품 목록 전체 조회
    Long updateProductStatus(Long productId, String status); // 상품 진행상태 수정
    Long updateProductAll(Long productId, Map<String, String> map); // 상품 게시글 전체 수정
    Long deleteProduct(Product product); // 상품 삭제
}

