package com.project.karrot.src.interest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestedRepository extends JpaRepository<InterestedProduct, Long> {

    Optional<List<InterestedProduct>> findByMemberId(Long memberId);

    Optional<List<InterestedProduct>> findByProductId(Long productId);

    Optional<Long> deleteByProductId(Long productId);

}
