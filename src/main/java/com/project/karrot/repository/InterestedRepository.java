package com.project.karrot.repository;

import com.project.karrot.domain.InterestedProduct;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterestedRepository extends JpaRepository<InterestedProduct, Long> {

    // findAll(), findById(id), save(object), saveAll(List), delete(object), deleteAll(List), count(), exists(id), flush()

    Optional<List<InterestedProduct>> findByMemberId(Long memberId);

    Optional<List<InterestedProduct>> findByProductId(Long productId);

}
