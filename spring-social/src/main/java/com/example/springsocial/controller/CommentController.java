package com.example.springsocial.controller;

import com.example.springsocial.dto.CommentDto;
import com.example.springsocial.model.Comment;
import com.example.springsocial.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDto createArticle(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        Comment comment = commentService.addComment(articleId, dto);

        return comment.toDto();
    }
}
