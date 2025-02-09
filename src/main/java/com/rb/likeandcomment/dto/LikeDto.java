package com.rb.likeandcomment.dto;

import com.rb.likeandcomment.util.LikeType;
import lombok.Data;

@Data
public class LikeDto {
    private String userId;
    private Integer postId;
    private LikeType type;
}
