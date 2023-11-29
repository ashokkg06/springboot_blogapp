package com.example.blogapp.blogapp.articles;

import java.util.Date;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;
//import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.blogapp.blogapp.users.UserEntity;

// import com.example.blogapp.blogapp.users.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "articles")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
// @Table(name = "articles")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String slug;

    @NonNull
    @Column(nullable = false)
    private String title;

    @Nullable
    @Column()
    private String subtitle;

    @NonNull
    @Column(nullable = false)
    private String body;

    @CreatedDate
    private Date createdAt;
    
    @ManyToOne
    @JoinColumn(name="authorId", nullable = false)
    private UserEntity author;

    //TODO: add tags
}
