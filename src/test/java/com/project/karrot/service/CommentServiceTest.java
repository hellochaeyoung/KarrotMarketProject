package com.project.karrot.service;

import com.project.karrot.src.comment.Comment;
import com.project.karrot.src.comment.CommentService;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;
    @Autowired
    CommentService commentService;

    @Test
    public void 댓글_등록() {
        Member member = new Member();
        memberService.join(member);

        Product product = new Product();
        product.setProductName("아이패드");
        product.setContents("중고 아이패드 팝니다.");
        product.setMember(member);

        Comment comment = new Comment();
        comment.setContents("제가 사고싶어요!");
        comment.setMember(member);

        Product registerProduct = productService.register(product);
        comment.setProduct(product);
        Comment comment1 = commentService.register(comment);

        Comment findComment = commentService.find(comment.getCommentId()).get();

        assertEquals(comment1.getContents(), registerProduct.getComments().get(0).getContents());
        assertEquals(registerProduct.getComments().get(0).getCommentId(), findComment.getCommentId());

    }

    @Test
    public void 댓글_삭제() {

        Member member = new Member();
        memberService.join(member);

        Product product = new Product();
        product.setProductName("아이패드");
        product.setContents("중고 아이패드 팝니다.");
        product.setMember(member);

        Comment comment = new Comment();
        comment.setContents("제가 사고싶어요!");
        comment.setMember(member);

        Product registerProduct = productService.register(product);
        comment.setProduct(registerProduct);
        Comment registerComment = commentService.register(comment);

        Comment findComment = commentService.find(registerComment.getCommentId()).get();

        //assertEquals(registerProduct.getComments().size(), 1);

        findComment.setProduct(null); // 연관관계 삭제
        commentService.remove(findComment);

        assertEquals(registerProduct.getComments().size(), 0);

    }
}
