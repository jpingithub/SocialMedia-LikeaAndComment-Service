package com.rb.likeandcomment.dto;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String profileImageUrl;
    private String email;
}
