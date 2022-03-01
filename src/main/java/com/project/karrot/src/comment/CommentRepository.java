package com.project.karrot.src.comment;

import com.project.karrot.src.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByProduct(Long productId);

    Optional<Comment> findByMemberAndProduct(Long memberId, Long productId);

}
