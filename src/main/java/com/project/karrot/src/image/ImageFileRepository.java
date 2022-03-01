package com.project.karrot.src.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFiles, Long> {

    Optional<MemberImageFile> findOneByMember(Long memberId);

    List<ProductImageFile> findAllByProduct(Long productId);


}
