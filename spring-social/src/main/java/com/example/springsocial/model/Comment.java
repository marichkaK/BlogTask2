package com.example.springsocial.model;

import com.example.springsocial.dto.CommentDto;
import com.example.springsocial.dto.UserDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "comment")
public class Comment implements Dto<CommentDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    public Comment(String text) {
        this.text = text;
    }

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    @Override
    public CommentDto toDto() {
        return CommentDto.builder()
            .id(id)
            .user(user.toDto())
            .text(text)
            .created(Date.from(created.atZone(ZoneId.systemDefault()).toInstant()))
            .build();
    }
}
