package com.project.karrot.src.comment;

import com.project.karrot.src.comment.dto.CommentRequestDto;
import com.project.karrot.src.comment.dto.CommentResponseDto;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public CommentResponseDto find(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> findByProductId(Long productId) {
        List<Comment> list = commentRepository.findByProductId(productId).orElseGet(ArrayList::new);

        return list.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDto register(CommentRequestDto commentRequestDto, Long memberId, Long productId) {

        Member loginMember = memberRepository.findById(memberId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        commentRequestDto.setCommentRequestDto(fomatDate());
        Comment uploadComment = commentRepository.save(commentRequestDto.toEntity(loginMember, product));;

        return new CommentResponseDto(uploadComment);
    }

    public String update(CommentRequestDto commentRequestDto, Long memberId, Long productId) {

        commentRepository.findByMemberIdAndProductId(memberId, productId).ifPresent(findComment -> {
            findComment.setContents(commentRequestDto.getContents());
            findComment.setTime(commentRequestDto.getTime());
        });

        return commentRequestDto.getContents();
    }

    public Optional<Comment> exist(Long memberId, Long productId) {
        return commentRepository.findByMemberIdAndProductId(memberId, productId);
    }

    public void remove(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }
}
