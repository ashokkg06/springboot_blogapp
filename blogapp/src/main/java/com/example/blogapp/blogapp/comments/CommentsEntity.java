package com.example.blogapp.blogapp.comments;

import java.util.Date;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
//import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.blogapp.blogapp.articles.ArticleEntity;
import com.example.blogapp.blogapp.users.UserEntity;

// import com.example.blogapp.blogapp.articles.ArticleEntity;
// import com.example.blogapp.blogapp.users.UserEntity;

import lombok.*;

@Entity(name = "comments")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
// @Table(name = "comments")
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    
    @Nullable
    @Column()
    private String title;

    @NonNull
    @Column(nullable = false)
    private String body;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name="articleId", nullable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name="authorId", nullable = false)
    private UserEntity author;
}
