package com.example.blogapp.blogapp.comments.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class CreateCommentRequest {
    private String title;
    private String body;
}
