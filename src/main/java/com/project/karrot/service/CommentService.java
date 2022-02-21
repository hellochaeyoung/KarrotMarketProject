package com.project.karrot.service;

import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.dto.CommentRequestDto;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.MemberResponseDto;
import com.project.karrot.dto.ProductRequestDto;
import com.project.karrot.repository.CommentRepository;
import com.project.karrot.repository.MemberRepository;
import com.project.karrot.repository.ProductRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public Optional<Comment> find(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Optional<List<Comment>> findByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }

    public Optional<Comment> exist(Long memberId, Long productId) {
        return commentRepository.findByMemberIdAndProductId(memberId, productId);
    }

    public Comment register(CommentRequestDto commentRequestDto, MemberResponseDto memberResponseDto) {

        exist(memberResponseDto.getId(), commentRequestDto.getProductId()).ifPresent(comment -> {
            comment.setContents(commentRequestDto.getContents());
            comment.setTime(commentRequestDto.getTime());
        });

        Member loginMember = memberRepository.findById(memberResponseDto.getId()).orElseThrow(); ////////////// 이렇게 DTO -> Entity로 변환하여 하는게 맞는지 모르겠음.
        Product product = productRepository.findById(commentRequestDto.getProductId()).orElseThrow(); //////////////

        commentRequestDto.setCommentRequestDto(loginMember, product, fomatDate());
        Comment uploadComment = commentRequestDto.toEntity();

        return commentRepository.save(uploadComment);
    }

    public void remove(CommentRequestDto commentRequestDto) {
        commentRepository.deleteById(commentRequestDto.getCommentId());
    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }
}
