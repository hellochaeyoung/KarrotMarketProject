package com.project.karrot.src.interest;

import com.project.karrot.src.interest.InterestedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestedRepository extends JpaRepository<InterestedProduct, Long> {

    // findAll(), findById(id), save(object), saveAll(List), delete(object), deleteAll(List), count(), exists(id), flush()

    Optional<List<InterestedProduct>> findByMemberId(Long memberId);

    Optional<List<InterestedProduct>> findByProductId(Long productId);

}
