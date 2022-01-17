package com.project.karrot.service;

import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Product;
import com.project.karrot.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> find(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Optional<List<Comment>> findByProduct(Product product) {
        return commentRepository.findByProduct(product);
    }

    public Comment register(Comment comment) {
        return commentRepository.save(comment);
    }

    public void remove(Comment comment) {
        commentRepository.delete(comment);
    }
}
