package com.example.springsocial.service;

import com.example.springsocial.model.ArticleTag;
import com.example.springsocial.repository.ArticleTagRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ArticleTagService {

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Transactional
    public List<ArticleTag> saveTags(String tagsString) {
        List<String> tagList = Stream.of(tagsString.split("[ ,#]"))
            .filter(s -> !StringUtils.isEmpty(s))
            .collect(Collectors.toList());
        Map<String, ArticleTag> tagsByName = articleTagRepository.findAllByNameIn(tagList)
            .stream()
            .collect(Collectors.toMap(ArticleTag::getName, Function.identity()));

        List<ArticleTag> tags = tagList.stream()
            .map(s -> Optional.ofNullable(tagsByName.get(s)).orElse(new ArticleTag(s)))
            .collect(Collectors.toList());

        return articleTagRepository.saveAll(tags);
    }
}
