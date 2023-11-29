package com.example.blogapp.blogapp.comments;

import org.springframework.stereotype.Service;

import com.example.blogapp.blogapp.articles.ArticlesRepository;
import com.example.blogapp.blogapp.comments.dtos.CreateCommentRequest;

@Service
public class CommentsService {
    private CommentRepository commentRepository;
    private ArticlesRepository articlesRepository;

    public CommentsService(CommentRepository commentRepository, ArticlesRepository articlesRepository) {
        this.commentRepository = commentRepository;
        this.articlesRepository = articlesRepository;
    }

    public Iterable<CommentsEntity> getAllComments() {
        return commentRepository.findAll();
    }

    public Iterable<CommentsEntity> getCommentsByArticleId(Long articleId) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        var comments = commentRepository.findByArticle(article);
        return comments;
    }

    public CommentsEntity createComment(Long articleId, CreateCommentRequest request) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        return commentRepository.save(CommentsEntity.builder()
                                .title(request.getTitle())
                                .body(request.getBody())
                                .author(article.getAuthor())
                                .article(article)
                                .build()
        );
    }

    public CommentsEntity getCommentById(Long commentId) {
        var comments = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        return comments;
    }

    public CommentsEntity updateComment(Long commentId, CreateCommentRequest request) {
        var comments = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        if(request.getTitle() != null)
            comments.setTitle(request.getTitle());
        if(request.getBody() != null)
            comments.setBody(request.getBody());
        return commentRepository.save(comments);
    }

    public class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(Long userId) {
            super("User with " + userId + " Not Found");
        }
    }

    public class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(Long articleId) {
            super("Article with " + articleId + " Not Found");
        }
    }

    public class CommentNotFoundException extends IllegalArgumentException {
        public CommentNotFoundException(Long commentId) {
            super("Comment with " + commentId + " Not Found");
        }
    }
}