package com.example.blogapp.blogapp.comments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.blogapp.articles.ArticlesService.ArticleNotFoundException;
import com.example.blogapp.blogapp.comments.CommentsService.CommentNotFoundException;
import com.example.blogapp.blogapp.comments.CommentsService.UserNotFoundException;
import com.example.blogapp.blogapp.comments.dtos.CreateCommentRequest;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    
    private final CommentsService commentsService;
    
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<CommentsEntity>> getAllComments() {
        var comments = commentsService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentsEntity> getCommentById(@PathVariable("id") Long commentId) {
        var comments = commentsService.getCommentById(commentId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommentsEntity> updateComment(@PathVariable("id") Long commentId, @RequestBody CreateCommentRequest request) {
        var comments = commentsService.updateComment(commentId, request);
        return ResponseEntity.ok(comments);
    }

    @ExceptionHandler({
        CommentsService.UserNotFoundException.class,
        CommentsService.ArticleNotFoundException.class,
        CommentsService.CommentNotFoundException.class
    })
    ResponseEntity<String> handleUserExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if(ex instanceof UserNotFoundException)
        {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if(ex instanceof ArticleNotFoundException)
        {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        
        else if(ex instanceof CommentNotFoundException)
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
