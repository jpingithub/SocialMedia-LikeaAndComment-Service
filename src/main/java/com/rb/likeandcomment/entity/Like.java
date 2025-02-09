package com.rb.likeandcomment.entity;

import com.rb.likeandcomment.util.LikeType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private Integer postId;
    private LikeType type;
    private String likedAt;
}
