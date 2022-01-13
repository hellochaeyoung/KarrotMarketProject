package com.project.karrot.repository;

import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.domain.ProductStatus;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JPAProductRepository implements ProductRepository{

    private final EntityManager em;

    public JPAProductRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Product save(Product product) {
        em.persist(product);

        return product;
    }

    @Override // 회원별 상품 조회
    public List<Product> findByMemberId(Long memberId) {
        return em.createQuery("select p from Product p join p.member m where m.id = :memberId", Product.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override // 지역별 상품 조회
    public List<Product> findByLocationId(Long locationId) {
        return em.createQuery("select p from Product p join p.location l where l.locationId = :locationId", Product.class)
                .setParameter("locationId", locationId)
                .getResultList();
    }

    @Override // 지역별 카테고리별 상품 조회
    public List<Product> findByLocationAndCategory(Long locationId, Long categoryId) {
        return em.createQuery("select p from Product p join p.location l where l.locationId = :locationId", Product.class)
                .setParameter("locationId", locationId)
                .getResultList().stream().filter(product -> product.getCategory().getCategoryId() == categoryId).collect(Collectors.toList());
    }

    @Override // 회원별 진행단계별 조회
    public List<Product> findByMemberAndStatus(Long memberId, String status) {
        return em.createQuery("select p from Product p join p.member m where m.id = :memberId", Product.class)
                .setParameter("memberId", memberId)
                .getResultList().stream().filter(product -> product.getProductStatus().name().equals(status)).collect(Collectors.toList());
    }

    @Override
    public List<Product> findInterestedProduct(Long memberId) {
        return em.createQuery("select p from Product p join p.comments c where c.member.id = :memberId", Product.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Product> findDealProduct(Long memberId) {
        return em.createQuery("select p from Product p join p.deal d where d.member.id = :memberId", Product.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Long updateProductStatus(Long productId, String status) {
        Product product = em.createQuery("select p from Product p where p.productId = :productId", Product.class)
                .setParameter("productId", productId)
                .getResultList().stream().findAny().get();

        ProductStatus productStatus = Arrays.stream(ProductStatus.values()).filter(status1 -> status1.name().equals(status)).findAny().get();
        product.setProductStatus(productStatus);

        return productId;
    }

    @Override
    public Long updateProductAll(Long productId, Map<String, String> map) {

        return null;
    }

    @Override
    public Long deleteProduct(Product product) {
        // null로 처리 해줘야함
        List<Member> members = em.createQuery("select m from Member m where m.products.productId = :productId", Member.class)
                .setParameter("productId", product.getProductId())
                .getResultList();

        for(Member member : members) {
            member.getProducts().remove(product);
        }

        em.remove(product);
        return null;
    }

    @Override // 상품 조회
    public Optional<Product> findByProductId(Long productId) {
        return em.createQuery("select p from Product p where p.productId = :productId", Product.class)
                .setParameter("productId", productId)
                .getResultList().stream().findAny();
    }

    @Override
    public int updateLikeCountOfProduct(Long ProductId, int count) {
        return 0;
    }

    @Override
    public List<Comment> findAllComment(Long productId) {
        return null;
    }
}
