package com.rb.likeandcomment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rb.likeandcomment.dto.CommentDto;
import com.rb.likeandcomment.entity.Comment;
import com.rb.likeandcomment.exception.CommentException;
import com.rb.likeandcomment.repositoty.CommentRepository;
import com.rb.likeandcomment.util.Checker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ObjectMapper objectMapper;
    private final Checker checker;

    public Comment commentOnPost(String username, CommentDto commentDto) {
        checker.checkUserExistence(username);
        checker.checkPostExistence(commentDto.getPostId());
        Comment comment = objectMapper.convertValue(commentDto, Comment.class);
        comment.setCommentedAt(Instant.now().toString());
        comment.setUserId(username);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsOf(Integer postId) {
        checker.checkPostExistence(postId);
        List<Comment> byPostId = commentRepository.findByPostId(postId);
        if (!byPostId.isEmpty()) {
            log.info("{} comments found on post : {}", byPostId.size(), postId);
            return byPostId;
        } else {
            log.info("No comments found for the post : {}", postId);
            throw new CommentException("No one commented on post : " + postId);
        }
    }

}
