package com.example.springsocial.controller;

import com.example.springsocial.model.Article;
import com.example.springsocial.model.Dto;
import com.example.springsocial.service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{startNumber}/{endNumber}")
    @PreAuthorize("hasRole('USER')")
    public List<?> getArticles(@PathVariable Long startNumber, @PathVariable Long endNumber) {
        List<Article> articles = articleService.getArticles(startNumber, endNumber);

        return Dto.toDtos(articles);
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('USER')")
    public Integer getArticlesCount() {
        return articleService.getArticlesCount();
    }

    @PostMapping(path = "/createArticle", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public Article createArticle(){
            return new Article();
    }
}
