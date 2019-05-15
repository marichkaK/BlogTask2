package com.example.springsocial.repository;

import com.example.springsocial.model.Article;
import com.example.springsocial.model.ArticleTag;
import com.example.springsocial.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Page<Article> findAllByTagsContains(Pageable pageable, ArticleTag articleTag);
}
