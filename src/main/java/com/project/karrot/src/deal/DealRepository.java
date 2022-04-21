package com.project.karrot.src.deal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {

    Optional<List<Deal>> findByMemberId(Long memberId);

    Optional<Deal> findByProductId(Long productId);

    Optional<Long> deleteByProductId(Long productId);

}
