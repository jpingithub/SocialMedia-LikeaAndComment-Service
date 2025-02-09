package com.rb.likeandcomment.client;

import com.rb.likeandcomment.dto.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "POST-SERVICE")
public interface PostClient {
    @GetMapping("/api/v1/posts/{id}")
    ResponseEntity<Post> getPostById(@PathVariable("id") Integer id);
}
