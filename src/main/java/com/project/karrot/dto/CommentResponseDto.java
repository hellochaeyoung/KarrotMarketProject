package com.project.karrot.dto;

import com.project.karrot.domain.Comment;
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
    private String contents;
    private String time;

    public CommentResponseDto(Comment comment) {
        this.nickName = comment.getMember().getNickName();
        this.commentId = comment.getCommentId();
        this.contents = comment.getContents();
        this.time = comment.getTime();
    }
}
