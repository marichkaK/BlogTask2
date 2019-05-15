package com.example.springsocial.model;

import java.time.Clock;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "to_email", nullable = false)
    private String toEmail;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "sent", nullable = false)
    private Boolean sent;

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now(Clock.systemUTC());
        this.sent = false;
    }

}
