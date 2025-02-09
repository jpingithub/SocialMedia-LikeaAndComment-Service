package com.rb.likeandcomment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rb.likeandcomment.dto.LikeDto;
import com.rb.likeandcomment.entity.Like;
import com.rb.likeandcomment.exception.LikeException;
import com.rb.likeandcomment.repositoty.LikeRepository;
import com.rb.likeandcomment.util.Checker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final ObjectMapper objectMapper;
    private final Checker checker;

    public Like likeAPost(LikeDto likeDto){
        checker.checkUserExistence(likeDto.getUserId());
        checker.checkPostExistence(likeDto.getPostId());
        checker.checkUserAlreadyLikedThePost(likeDto.getUserId(),likeDto.getPostId());
        Like like = objectMapper.convertValue(likeDto, Like.class);
        like.setLikedAt(Instant.now().toString());
        return likeRepository.save(like);
    }

    public List<Like> getLikesOf(Integer postId){
        checker.checkPostExistence(postId);
        List<Like> byPostId = likeRepository.findByPostId(postId);
        if (!byPostId.isEmpty()) {
            log.info("{} Likes found for the post : {} ",byPostId.size(),postId);
            return byPostId;
        } else {
            log.info("No likes found for the post : {}",postId);
            throw new LikeException("No one has liked this post yet");
        }
    }


}
