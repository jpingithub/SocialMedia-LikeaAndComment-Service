package com.rb.likeandcomment.client;

import com.rb.likeandcomment.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-MANAGEMENT")
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    ResponseEntity<User> getUserById(@PathVariable("id") String userId);

}