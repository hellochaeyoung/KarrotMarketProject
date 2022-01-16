package com.project.karrot.repository;

import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query( "select c from Comment c where c.product = :product")
    List<Comment> findByProduct(@Param("product") Product product);

}
