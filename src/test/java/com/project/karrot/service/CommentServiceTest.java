package com.project.karrot.service;

import com.project.karrot.src.comment.CommentService;
import com.project.karrot.src.comment.dto.CommentRequestDto;
import com.project.karrot.src.comment.dto.CommentResponseDto;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    @Autowired
    CommentService commentService;

    private Long memberId = 9L;
    private Long productId = 3L;
    private CommentRequestDto commentRequestDto;
    private CommentResponseDto commentResponseDto;

    @BeforeEach
    void init() {

        memberId = 9L;

        commentRequestDto = CommentRequestDto.builder()
                .contents("제가 사고싶어요!")
                .build();

        commentResponseDto = commentService.register(commentRequestDto, memberId, productId);

        commentRequestDto.setCommentId(commentResponseDto.getCommentId()); // 요청 dto로 이미 등록된 댓글 정보가 들어왔다 가정하기 위해 설정

    }

    @Test
     void 댓글_등록() {

        Long registeredId = commentResponseDto.getProductId();
        MemberResponseDto member = memberService.findByNickName(commentResponseDto.getNickName());

        assertEquals(productId, registeredId);
        assertEquals(memberId, member.getId());

    }

    @Test
    void 댓글_수정() {

        String content = "제가 정말정말 사고싶어요!";
        CommentRequestDto requestDto = CommentRequestDto.builder().commentId(1L).contents(content).build();
        String updateContent = commentService.update(requestDto, memberId, productId);

        assertEquals(updateContent, content);
    }

    @Test
    void 댓글_삭제() {

        commentService.remove(commentRequestDto.getCommentId());

        Assertions.assertThatThrownBy(() ->
                commentService.find(commentRequestDto.getCommentId())).isInstanceOf(NoSuchElementException.class);


    }

}
