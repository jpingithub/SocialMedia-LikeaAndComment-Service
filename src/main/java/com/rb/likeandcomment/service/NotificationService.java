package com.rb.likeandcomment.service;

import com.rb.likeandcomment.client.PostClient;
import com.rb.likeandcomment.client.UserClient;
import com.rb.likeandcomment.dto.Event;
import com.rb.likeandcomment.dto.User;
import com.rb.likeandcomment.util.LikeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserClient userClient;
    @Autowired
    private PostClient postClient;

    @Value("${messaging.exchange}")
    private String EXCHANGE_NAME;
    @Value("${messaging.routing-key}")
    private String ROUTING_KEY;
    @Value("${mailing.comment-subject-text}")
    private String commentSubject;
    @Value("${mailing.content.comment-text}")
    private String commentContent;
    @Value("${mailing.like-subject-text}")
    private String likeSubject;
    @Value("${mailing.content.like-text}")
    private String likeContent;

    public void publishCommentEvent(String loggedInUser, Integer postId, String comment) {
        Event toRabbitEvent = getEventToPostToRabbit(postId);
        toRabbitEvent.setSubject(commentSubject);
        toRabbitEvent.setContent(loggedInUser+" " + commentContent + comment);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, toRabbitEvent);
        log.info("Comment message sent to rabbit MQ");
    }

    public void publishCommentEvent(String loggedInUser, Integer postId, LikeType type) {
        Event toRabbit = getEventToPostToRabbit(postId);
        toRabbit.setSubject(likeSubject);
        toRabbit.setContent(loggedInUser+" "+likeContent+" "+type.toString());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, toRabbit);
        log.info("Like message sent to rabbit MQ");
    }

    private String getPostOwnerUsername(Integer postId) { 
        return postClient.getPostById(postId).getBody().getPostedBy();
    }
    
    private Event getEventToPostToRabbit(Integer postId){
        final Event event = new Event();
        String username = getPostOwnerUsername(postId);
        ResponseEntity<User> userResponseEntity = userClient.searchUser(username);
        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            String email = userResponseEntity.getBody().getEmail();
            event.setToMail(email);
        }
        return event;
    }

}
