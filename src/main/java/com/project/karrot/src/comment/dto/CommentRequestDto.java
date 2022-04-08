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
    private String contents;
    private String time;

    private Member member;
    private Product product;

    public Comment toEntity() {
        return Comment.builder()
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
