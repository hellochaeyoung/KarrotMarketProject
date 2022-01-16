package com.project.karrot.repository;

import com.project.karrot.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFiles, Long> {

    @Query( "select f from ImageFiles f where f.member = :member")
    Optional<MemberImageFile> findOneByMember(@Param("member") Member member);

    @Query( "select f from ImageFiles f where f.product = :product")
    List<ProductImageFile> findAllByProducts(@Param("product") Product product);


}
