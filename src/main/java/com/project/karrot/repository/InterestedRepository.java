package com.project.karrot.repository;

import com.project.karrot.domain.InterestedProduct;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestedRepository extends JpaRepository<InterestedProduct, Long> {

    // findAll(), findById(id), save(object), saveAll(List), delete(object), deleteAll(List), count(), exists(id), flush()

    // findByMember
    @Query( "select i from InterestedProduct i where i.member = :member" )
    List<InterestedProduct> findByMember(@Param("member") Member member);

    // findByProduct
    @Query( "select i from InterestedProduct i where i.product = :product")
    List<InterestedProduct> findByProduct(@Param("product") Product product);

}
