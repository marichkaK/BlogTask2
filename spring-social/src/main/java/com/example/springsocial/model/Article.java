package com.example.springsocial.model;

import com.example.springsocial.dto.ArticleDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article implements Dto<ArticleDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToMany
    private List<ArticleTag> tags;

    @OneToMany(mappedBy = "article")
    private List<ArticleLike> likes;

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    @Override
    public ArticleDto toDto() {
        return ArticleDto.builder()
            .id(id)
            .name(name)
            .owner(user.toDto())
            .content(content)
            .created(Date.from(created.atZone(ZoneId.systemDefault()).toInstant()))
            .likes(Dto.toDtos(likes))
            .tags(Dto.toDtos(tags))
            .build();
    }
}
