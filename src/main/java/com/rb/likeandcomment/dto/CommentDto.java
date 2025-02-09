package com.rb.likeandcomment.dto;

import lombok.Data;

@Data
public class CommentDto {
    private String userId;
    private Integer postId;
    private String comment;
}
