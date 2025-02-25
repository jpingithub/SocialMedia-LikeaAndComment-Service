package com.rb.likeandcomment.util;

import com.rb.likeandcomment.client.PostClient;
import com.rb.likeandcomment.client.UserClient;
import com.rb.likeandcomment.exception.PostException;
import com.rb.likeandcomment.exception.UserException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Checker {


    private final UserClient userClient;
    private final PostClient postClient;

    public void checkUserExistence(String userId) throws UserException {
        try {
            userClient.getUserById(userId);
        } catch (FeignException.NotFound ex) {
            log.info("No user found to like");
            throw new UserException("No user found with id : " + userId);
        }
    }

    public void checkPostExistence(Integer postId) throws PostException {
        try {
            postClient.getPostById(postId);
        } catch (FeignException.BadRequest ex) {
            log.info("No post found to like");
            throw new PostException("No post found with id : " + postId);
        }
    }

}
