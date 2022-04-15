package com.project.karrot.src.comment.dto;

import com.project.karrot.src.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private String nickName;
    private Long commentId;
    private Long productId;
    private String contents;
    private String time;

    public CommentResponseDto(Comment comment) {
        this.nickName = comment.getMember().getNickName();
        this.commentId = comment.getId();
        this.productId = comment.getProduct().getId();
        this.contents = comment.getContents();
        this.time = comment.getTime();
    }
}
