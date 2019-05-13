package com.example.springsocial.service;

import com.example.springsocial.dto.NewArticleDto;
import com.example.springsocial.model.Article;
import com.example.springsocial.model.ArticleTag;
import com.example.springsocial.repository.ArticleRepository;
import com.example.springsocial.util.SecurityUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTagService articleTagService;

    public Page<Article> getArticles(int page, int size) {
        return articleRepository.findAll(PageRequest.of(page, size, Sort.by("created").descending()));
    }

    @Transactional
    public Article createArticle(NewArticleDto dto) {
        Article article = dto.toArticle();
        article.setUser(SecurityUtil.currentUser());

        if (!StringUtils.isEmpty(dto.getTags())) {
            List<ArticleTag> articleTags = articleTagService.saveTags(dto.getTags());
            article.setTags(articleTags);
        }

        return articleRepository.save(article);
    }
}
