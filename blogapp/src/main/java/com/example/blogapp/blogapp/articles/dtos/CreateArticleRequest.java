package com.example.blogapp.blogapp.articles.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class CreateArticleRequest {
    private String title;
    private String body;
    private String subtitle;
}
