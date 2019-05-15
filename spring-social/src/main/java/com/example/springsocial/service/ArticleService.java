package com.example.springsocial.service;

import com.example.springsocial.dto.ArticlePageDto;
import com.example.springsocial.dto.NewArticleDto;
import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.Article;
import com.example.springsocial.model.ArticleTag;
import com.example.springsocial.model.Dto;
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

    private static final int SHORT_DESCRIPTION_LENGTH = 100;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTagService articleTagService;

    public ArticlePageDto getArticles(int page, int size, String tag) {
        Page<Article> articlePage;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("created").descending());
        if (!StringUtils.isEmpty(tag) && !"none".equals(tag)) {
            ArticleTag articleTag = articleTagService.findByName(tag);
            articlePage = articleRepository.findAllByTagsContains(pageRequest, articleTag);
        } else {
            articlePage = articleRepository.findAll(pageRequest);
        }

        List<Article> articles = articlePage.getContent();
        articles.forEach(this::shortVersionedArticle);

        return new ArticlePageDto(articlePage.getTotalElements(), Dto.toDtos(articles));
    }

    private void shortVersionedArticle(Article article) {
        if (article.getContent() != null && article.getContent().length() > SHORT_DESCRIPTION_LENGTH) {
            String shortDescription = article.getContent().substring(0, SHORT_DESCRIPTION_LENGTH);

            article.setContent(shortDescription + "...");
        }
    }

    public Article getArticle(Long id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public Article createArticle(NewArticleDto dto) {
        Article article = dto.toArticle();
        article.setUser(SecurityUtil.currentUser());
        article.setContent(article.getContent().replaceAll("\n", "<br>"));

        if (!StringUtils.isEmpty(dto.getTags())) {
            List<ArticleTag> articleTags = articleTagService.saveTags(dto.getTags());
            article.setTags(articleTags);
        }

        return articleRepository.save(article);
    }
}
