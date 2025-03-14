package com.rb.likeandcomment.util;

import com.rb.likeandcomment.client.PostClient;
import com.rb.likeandcomment.client.UserClient;
import com.rb.likeandcomment.dto.User;
import com.rb.likeandcomment.exception.PostException;
import com.rb.likeandcomment.exception.UserException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Checker {


    private final UserClient userClient;
    private final PostClient postClient;

    public void checkUserExistence(String username) throws UserException {
        ResponseEntity<User> userResponseEntity = userClient.searchUser(username);
        if(userResponseEntity.getStatusCode()== HttpStatus.OK) {
            log.info("User found with username : {}",username);
        } else{
            log.info("No user found to like");
            throw new UserException("No user found with username : " + username);
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
