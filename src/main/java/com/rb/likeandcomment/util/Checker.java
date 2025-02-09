package com.rb.likeandcomment.util;

import com.rb.likeandcomment.client.PostClient;
import com.rb.likeandcomment.client.UserClient;
import com.rb.likeandcomment.entity.Like;
import com.rb.likeandcomment.exception.LikeException;
import com.rb.likeandcomment.exception.PostException;
import com.rb.likeandcomment.exception.UserException;
import com.rb.likeandcomment.repositoty.CommentRepository;
import com.rb.likeandcomment.repositoty.LikeRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class Checker {


    private final UserClient userClient;
    private final PostClient postClient;
    private final LikeRepository likeRepository;

    public void checkUserAlreadyLikedThePost(String userId,Integer postId){
        Optional<Like> byUserIdAndPostId = likeRepository.findByUserIdAndPostId(userId, postId);
        if(byUserIdAndPostId.isPresent()){
            log.info("User already Liked this post");
            throw new LikeException("User : "+userId+", already liked the post : "+postId);
        }else{
            log.info("User : {} allowed to like the post : {}",userId,postId);
        }
    }

    public void checkUserExistence(String userId) throws UserException {
        try{
            userClient.getUserById(userId);
        }catch (FeignException.BadRequest ex){
            log.info("No user found to like");
            throw new UserException("No user found with id : "+userId);
        }
    }

    public void checkPostExistence(Integer postId) throws PostException {
        try{
            postClient.getPostById(postId);
        }catch (FeignException.BadRequest ex){
            log.info("No post found to like");
            throw new PostException("No post found with id : "+postId);
        }
    }

}
