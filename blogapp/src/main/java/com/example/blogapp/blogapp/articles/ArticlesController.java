package com.example.blogapp.blogapp.articles;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.blogapp.articles.ArticlesService.ArticleNotFoundException;
import com.example.blogapp.blogapp.articles.dtos.CreateArticleRequest;
import com.example.blogapp.blogapp.articles.dtos.UpdateArticleRequest;
import com.example.blogapp.blogapp.comments.CommentsEntity;
import com.example.blogapp.blogapp.comments.CommentsService;
import com.example.blogapp.blogapp.comments.dtos.CreateCommentRequest;
import com.example.blogapp.blogapp.security.JWTAuthentication;
import com.example.blogapp.blogapp.security.JWTService;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    
    private final ArticlesService articlesService;
    private final CommentsService commentsService;
    private final JWTService jwtService;

    public ArticlesController(ArticlesService articlesService, CommentsService commentsService, JWTService jwtService) {
        this.articlesService = articlesService;
        this.commentsService = commentsService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    ResponseEntity<Iterable<ArticleEntity>> getAllArticles()
    {
        var article = articlesService.getAllArticles();
        return ResponseEntity.ok(article);
    }

    @GetMapping("/{id}")
    ResponseEntity<ArticleEntity> getArticlesById(@PathVariable("id") Long id)
    {
        var article = articlesService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    @GetMapping("/slug/{slug}")
    ResponseEntity<ArticleEntity> getArticlesBySlug(@PathVariable("slug") String slug)
    {
        var article = articlesService.getArticleBySlug(slug);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/{id}")
    ResponseEntity<ArticleEntity> updateArticles(@PathVariable("id") Long id, @RequestBody UpdateArticleRequest req)
    {
        var article = articlesService.updateArticle(id, req);
        return ResponseEntity.ok(article);
    }

    @PostMapping("")
    ResponseEntity<ArticleEntity> createArticles(@RequestBody CreateArticleRequest req, Authentication authentication)
    {
        var jwtAuthentication = (JWTAuthentication) authentication;
        var jwt = jwtAuthentication.getCredentials();
        var userId = jwtService.retrieveUserId(jwt);
        var article = articlesService.createArticle(userId, req);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/{articleId}/comments")
    ResponseEntity<CommentsEntity> createComments(@PathVariable("articleId") Long articleId, @RequestBody CreateCommentRequest request) {
        var comment = commentsService.createComment(articleId, request);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{articleId}/comments")
    ResponseEntity<Iterable<CommentsEntity>> getCommentsByArticleId(@PathVariable("articleId") Long articleId) {
        var comments = commentsService.getCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }
    
    @ExceptionHandler({
        ArticlesService.ArticleNotFoundException.class
    })
    ResponseEntity<String> handleUserExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if(ex instanceof ArticleNotFoundException)
        {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else 
        {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(message);
    }
}