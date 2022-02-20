package com.project.karrot.service;

import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.dto.ProductRequestDto;
import com.project.karrot.repository.CommentRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> find(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Optional<List<Comment>> findByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }

    public Optional<Long> exist(Long memberId, Long productId) {
        return commentRepository.findByMemberIdAndProductId(memberId, productId);
    }

    public Comment register(Comment comment) {

        comment.setTime(fomatDate());

        return commentRepository.save(comment);
    }

    public void remove(Comment comment) {
        commentRepository.delete(comment);
    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }
}
