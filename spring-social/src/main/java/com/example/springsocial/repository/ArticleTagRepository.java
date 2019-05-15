package com.example.springsocial.repository;

import com.example.springsocial.model.ArticleTag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    List<ArticleTag> findAllByNameIn(List<String> names);

    Optional<ArticleTag> findByName(String name);
}
