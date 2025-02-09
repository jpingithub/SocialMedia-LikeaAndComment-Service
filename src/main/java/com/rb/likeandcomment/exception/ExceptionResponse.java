package com.rb.likeandcomment.exception;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String message;
    private String path;
    private String timeStamp;
    private Integer status;
}