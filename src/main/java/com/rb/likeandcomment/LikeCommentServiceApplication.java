package com.rb.likeandcomment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class LikeCommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikeCommentServiceApplication.class, args);
	}

}
