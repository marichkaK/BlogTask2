package com.example.springsocial.service;

import com.example.springsocial.model.Article;
import com.example.springsocial.model.ArticleTag;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.springsocial.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getArticles(Long startNumber, Long endNumber) {
        List<Article> articleList = new ArrayList<>();
        for(int i = startNumber.intValue(); i < endNumber.intValue();i++){
            if(i == 2){
                if(articleRepository.findById((long)i).isPresent()) {
                    articleList.add(articleRepository.findById((long) i).get());
                    if(articleRepository.findById((long)i+1).isPresent()) {
                        articleList.add(articleRepository.findById((long) i+1).get());
                    }
                }
            }else {
                if(articleRepository.findById((long)i).isPresent()) {
                    articleList.add(articleRepository.findById((long) i).get());
                }
            }
        }
        return  articleList;
    }

    public int getArticlesCount() {
        return (int)articleRepository.count();
    }
}
