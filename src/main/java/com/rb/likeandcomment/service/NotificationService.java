package com.rb.likeandcomment.service;

import com.rb.likeandcomment.client.NotificationClient;
import com.rb.likeandcomment.client.PostClient;
import com.rb.likeandcomment.client.UserClient;
import com.rb.likeandcomment.dto.Post;
import com.rb.likeandcomment.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationClient notificationClient;
    private final PostClient postClient;
    private final UserClient userClient;

    public void sendLikedNotification(Integer postId,String userId,String reaction){
        Post post = postClient.getPostById(postId).getBody();
        User user = userClient.getUserById(post.getPostedBy()).getBody();
        User reactedUser = userClient.getUserById(userId).getBody();
        String content = reactedUser.getUsername()+" has reacted with "+reaction+" to your post";
        notificationClient.sendEmail(user.getEmail(),content,"REACTION");
    }

}
