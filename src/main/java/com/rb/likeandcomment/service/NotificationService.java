package com.rb.likeandcomment.service;

import com.rb.likeandcomment.client.NotificationClient;
import com.rb.likeandcomment.client.PostClient;
import com.rb.likeandcomment.client.UserClient;
import com.rb.likeandcomment.dto.Post;
import com.rb.likeandcomment.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationClient notificationClient;
    private final PostClient postClient;
    private final UserClient userClient;

    LocalDateTime now = LocalDateTime.now();
    String time = now.getDayOfMonth() + "th " + now.getMonth() + "," + now.getYear() + " " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();

    public void sendNotification(Integer postId, String userId, String reaction) {
        Post post = postClient.getPostById(postId).getBody();
        User user = userClient.getUserById(post.getPostedBy()).getBody();
        User reactedUser = userClient.getUserById(userId).getBody();
        String content = reactedUser.getUsername() + " has reacted with " + reaction + " to your post on " + time;
        log.info("Like Mail sent to : {}", user.getEmail());
        notificationClient.sendEmail(user.getEmail(), content, "you got REACTION");
    }

    public void sendNotification(Integer postId, String userId) {
        Post post = postClient.getPostById(postId).getBody();
        User user = userClient.getUserById(post.getPostedBy()).getBody();
        User commentedUser = userClient.getUserById(userId).getBody();
        String content = commentedUser.getUsername() + " has commented on your post on " + time;
        log.info("Comment Mail sent to : {}", user.getEmail());
        notificationClient.sendEmail(user.getEmail(), content, "you got COMMENT");
    }

}
