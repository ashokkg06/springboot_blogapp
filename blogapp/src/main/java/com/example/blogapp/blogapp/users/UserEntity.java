package com.example.blogapp.blogapp.users;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;

@Entity(name = "users")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor()
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String username;

    @Column(nullable = true)
    @Nullable
    private String password;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = true)
    @Nullable
    private String bio;

    @Column(nullable = true)
    @Nullable
    private String image;
}