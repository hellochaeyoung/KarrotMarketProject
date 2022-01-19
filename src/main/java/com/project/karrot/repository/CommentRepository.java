package com.project.karrot.repository;

import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query( "select c from Comment c where c.product = :product")
    Optional<List<Comment>> findByProduct(@Param("product") Product product);

    @Query( "select c.commentId from Comment c where c.member = :member and c.product = :product")
    Optional<Long> findByMemberAndProduct(@Param("member") Member member, @Param("product") Product product);

}
