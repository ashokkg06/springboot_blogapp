package com.example.blogapp.blogapp.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.blogapp.blogapp.articles.ArticleEntity;


@Repository
public interface CommentRepository extends JpaRepository<CommentsEntity, Long> {

    List<CommentsEntity> findByArticle(ArticleEntity article);
}
