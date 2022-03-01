package com.project.karrot.src.comment;

import com.project.karrot.src.comment.dto.CommentRequestDto;
import com.project.karrot.src.comment.dto.CommentResponseDto;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.product.ProductRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public CommentResponseDto find(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> findByProductId(Long productId) {
        List<Comment> list = commentRepository.findByProduct(productId).orElseGet(ArrayList::new);

        return list.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public Optional<Comment> exist(Long memberId, Long productId) {
        return commentRepository.findByMemberAndProduct(memberId, productId);
    }

    public CommentResponseDto register(CommentRequestDto commentRequestDto, MemberResponseDto memberResponseDto) {

        exist(memberResponseDto.getId(), commentRequestDto.getProductId()).ifPresent(comment -> {
            comment.setContents(commentRequestDto.getContents());
            comment.setTime(commentRequestDto.getTime());
        });

        Member loginMember = memberRepository.findById(memberResponseDto.getId()).orElseThrow(); ////////////// 이렇게 DTO -> Entity로 변환하여 하는게 맞는지 모르겠음.
        Product product = productRepository.findById(commentRequestDto.getProductId()).orElseThrow(); //////////////

        commentRequestDto.setCommentRequestDto(loginMember, product, fomatDate());
        Comment uploadComment = commentRepository.save(commentRequestDto.toEntity());;

        return new CommentResponseDto(uploadComment);
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
