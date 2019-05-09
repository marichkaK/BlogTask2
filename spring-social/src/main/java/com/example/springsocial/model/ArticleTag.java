package com.example.springsocial.model;

import com.example.springsocial.dto.ArticleTagDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article_tag", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name")
})
public class ArticleTag implements Dto<ArticleTagDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public ArticleTagDto toDto() {
        return ArticleTagDto.builder()
            .name(name)
            .build();
    }
}
