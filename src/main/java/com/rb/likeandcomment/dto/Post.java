package com.rb.likeandcomment.dto;

import lombok.Data;

@Data
public class Post {
    private Integer id;
    private String content;
    private String imageUrl;
    private String postedBy;
    private String createdAt;
}
