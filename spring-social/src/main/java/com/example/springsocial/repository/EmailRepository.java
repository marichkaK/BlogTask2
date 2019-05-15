package com.example.springsocial.repository;

import com.example.springsocial.model.Email;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {

    List<Email> findAllBySent(Boolean sent);
}
