package com.example.springsocial.controller;

import com.example.springsocial.dto.ArticleDto;
import com.example.springsocial.dto.ArticlePageDto;
import com.example.springsocial.dto.NewArticleDto;
import com.example.springsocial.model.Article;
import com.example.springsocial.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable Long id) {
        Article article = articleService.getArticle(id);

        return ResponseEntity.ok(article.toDto());
    }

    @GetMapping("/page/{page}/size/{size}/tags/{tag}")
    public ArticlePageDto getArticles(
        @PathVariable Integer page,
        @PathVariable Integer size,
        @PathVariable String tag) {

        return articleService.getArticles(page, size, tag);
    }

    @PostMapping
    public ArticleDto createArticle(@RequestBody NewArticleDto dto) {
        Article article = articleService.createArticle(dto);

        return article.toDto();
    }
}
