package com.example.springsocial.controller;

import com.example.springsocial.dto.ArticleDto;
import com.example.springsocial.dto.ArticlePageDto;
import com.example.springsocial.dto.NewArticleDto;
import com.example.springsocial.model.Article;
import com.example.springsocial.model.Dto;
import com.example.springsocial.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/{page}/{size}")
    public ArticlePageDto getArticles(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Article> articlePage = articleService.getArticles(page, size);

        return new ArticlePageDto(articlePage.getTotalElements(), Dto.toDtos(articlePage.getContent()));
    }

    @PostMapping
    public ArticleDto createArticle(@RequestBody NewArticleDto dto) {
        Article article = articleService.createArticle(dto);

        return article.toDto();
    }
}
