package com.project.karrot.src.comment.dto;

import com.project.karrot.src.comment.Comment;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    private Long commentId;
    private Long productId;
    private String contents;

    private String time;
    private Member member;
    private Product product;

    private Long memberId;

    public Comment toEntity() {
        return Comment.builder()
                .commentId(commentId)
                .contents(contents)
                .time(time)
                .member(member)
                .product(product)
                .build();
    }

    public void setCommentRequestDto(Member member, Product product, String time) {
        this.member = member;
        this.product = product;
        this.time = time;
    }

}
