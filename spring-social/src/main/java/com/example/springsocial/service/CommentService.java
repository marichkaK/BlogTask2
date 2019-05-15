package com.example.springsocial.service;

import com.example.springsocial.dto.CommentDto;
import com.example.springsocial.model.Article;
import com.example.springsocial.model.Comment;
import com.example.springsocial.repository.CommentRepository;
import com.example.springsocial.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Comment addComment(Long articleId, CommentDto dto) {
        Comment comment = dto.toComment();
        comment.setUser(SecurityUtil.currentUser());
        comment.setText(comment.getText().replaceAll("\n", "<br>"));

        Article article = articleService.getArticle(articleId);
        comment.setArticle(article);

        if (!article.getUser().getId().equals(comment.getUser().getId())) {
            emailService.sendNewCommentNotification(
                comment.getUser().getName(),
                article.getName(),
                comment.getUser().getEmail()
            );
        }

        return commentRepository.save(comment);
    }
}
