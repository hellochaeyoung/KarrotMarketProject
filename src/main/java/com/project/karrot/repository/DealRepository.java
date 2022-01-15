package com.project.karrot.repository;

import com.project.karrot.domain.Deal;
import com.project.karrot.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    @Query(" select d from Deal d where d.member = :member ")
    List<Deal> findByMember(@Param("member") Member member);

}
