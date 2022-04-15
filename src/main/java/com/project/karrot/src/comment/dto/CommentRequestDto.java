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

    public Comment toEntity(Member member, Product product) {
        return Comment.builder()
                .contents(contents)
                .time(time)
                .member(member)
                .product(product)
                .build();
    }

    public void setCommentRequestDto(String time) {
        this.time = time;
    }

}
