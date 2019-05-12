package com.example.springsocial.repository;

import com.example.springsocial.model.Article;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

}
