package com.example.springsocial.dto;

import com.example.springsocial.model.Comment;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private UserDto user;
    private String text;
    private Date created;

    public Comment toComment() {
        return new Comment(text);
    }
}
