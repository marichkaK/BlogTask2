package com.example.springsocial.dto;

import com.example.springsocial.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewArticleDto {

    private String name;
    private String content;
    private String tags;

    public Article toArticle() {
        return new Article(name, content);
    }
}
