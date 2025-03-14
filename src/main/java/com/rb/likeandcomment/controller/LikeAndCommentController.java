package com.rb.likeandcomment.controller;

import com.rb.likeandcomment.dto.CommentDto;
import com.rb.likeandcomment.dto.LikeDto;
import com.rb.likeandcomment.entity.Comment;
import com.rb.likeandcomment.entity.Like;
import com.rb.likeandcomment.service.CommentService;
import com.rb.likeandcomment.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/like-comment")
@RequiredArgsConstructor
public class LikeAndCommentController {

    private final LikeService likeService;
    private final CommentService commentService;

    @PostMapping("/likes")
    public ResponseEntity<Like> like(@RequestHeader("${customized-header-for-token}")String userName, @RequestBody LikeDto likeDto){
        return new ResponseEntity<>(likeService.likeAPost(userName,likeDto), HttpStatus.OK);
    }

    @GetMapping("/likes/{postId}")
    public ResponseEntity<List<Like>> getLikesOfPost(@PathVariable("postId") Integer postId){
        return new ResponseEntity<>(likeService.getLikesOf(postId),HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> comment(@RequestHeader("${customized-header-for-token}")String userName,@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.commentOnPost(userName,commentDto), HttpStatus.OK);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<Comment>> getCommentsOf(@PathVariable("postId")Integer postId){
        return new ResponseEntity<>(commentService.getCommentsOf(postId), HttpStatus.OK);
    }

}
