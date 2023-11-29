package com.example.blogapp.blogapp.users.dtos;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String username;
    private String password;
}
