package com.project.karrot.repository;

import com.project.karrot.domain.Deal;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {

    Optional<List<Deal>> findByMemberId(Long memberId);

    Optional<Deal> findByProductId(Long productId);
}
