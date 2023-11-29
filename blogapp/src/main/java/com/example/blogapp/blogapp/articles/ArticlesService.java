package com.example.blogapp.blogapp.articles;

import org.springframework.stereotype.Service;

import com.example.blogapp.blogapp.articles.dtos.CreateArticleRequest;
import com.example.blogapp.blogapp.articles.dtos.UpdateArticleRequest;
import com.example.blogapp.blogapp.users.UsersRepository;
import com.example.blogapp.blogapp.users.UsersService;
import com.github.slugify.Slugify;

@Service
public class ArticlesService {
    private ArticlesRepository articlesRepository;
    private UsersRepository usersRepository;
    
    public ArticlesService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles()
    {
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug)
    {
        var article = articlesRepository.findBySlug(slug);
        if(article == null)
            throw new ArticleNotFoundException(slug);
        return article;
    }

    public ArticleEntity getArticleById(Long articleId)
    {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        return article;
    }

    public ArticleEntity createArticle(Long authorId, CreateArticleRequest req)
    {
        var author = usersRepository.findById(authorId).orElseThrow(() -> new UsersService.UserNotFoundException(authorId));
        Slugify slg = Slugify.builder().build();
        // System.out.println("Slug: " + slg.slugify("how to add slug").toString());
        return articlesRepository.save(ArticleEntity.builder()
                        .title(req.getTitle())
                        .slug(slg.slugify(req.getTitle()).toString())
                        .body(req.getBody())
                        .subtitle(req.getSubtitle())
                        .author(author)
                        .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest req)
    {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if(req.getTitle() != null)
        {
            article.setTitle(req.getTitle());
            article.setSlug(req.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }
        if(req.getBody() != null)
            article.setBody(req.getBody());
        if(req.getSubtitle() != null)
            article.setSubtitle(req.getSubtitle());
        
        return articlesRepository.save(article);
    }

    public static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug)
        {
            super("Article " + slug + " Not Found");
        }

        public ArticleNotFoundException(Long articleId)
        {
            super("Article " + articleId + " Not Found");
        }
    }
}
