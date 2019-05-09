package com.example.springsocial.service;

import com.example.springsocial.model.Article;
import com.example.springsocial.model.ArticleTag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {

    private final List<Article> articles;

    private List<ArticleTag> tags1 = Arrays.asList(new ArticleTag(1L, "movie"), new ArticleTag(2L, "fun"));
    private List<ArticleTag> tags2 = Arrays.asList(new ArticleTag(2L, "movie"), new ArticleTag(3L, "hobby"));

    public ArticleService() {
        articles = new ArrayList<>();
        articles.add(new Article(1L, "HBO - Game of thrones", "The best movie ever!!!", LocalDateTime.now(), tags1, Collections.emptyList()));
        articles.add(new Article(1L, "Ski Sport", "St. Anton is a good place for skiing.", LocalDateTime.now(), tags2, Collections.emptyList()));

        for (int i = 0; i < 11; i++) {
            articles.add(new Article((long) i, i + " - Ski Sport", "St. Anton is a good place for skiing.", LocalDateTime.now(), tags2, Collections.emptyList()));
        }
    }

    public List<Article> getArticles(Long startNumber, Long endNumber) {
        return articles.subList(startNumber.intValue(), endNumber.intValue());
    }

    public int getArticlesCount() {
        return articles.size();
    }
}
