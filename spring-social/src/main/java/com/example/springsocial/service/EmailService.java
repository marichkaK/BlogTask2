package com.example.springsocial.service;

import com.example.springsocial.model.Email;
import com.example.springsocial.repository.EmailRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {

    @Autowired
    private SendEmailTLS sendEmailTLS;

    @Autowired
    private EmailRepository emailRepository;

    @Transactional
    public void sendNewCommentNotification(String commenterName, String articleName, String toEmail) {
        Email email = Email.builder()
            .subject("[Articles] New Comment")
            .text(String.format("You have new comment from %s in your '%s' article!", commenterName, articleName))
            .toEmail(toEmail)
            .build();

        emailRepository.save(email);
    }

    @Scheduled(cron = "0/30 * * * * *")
    public void sendEmails()
        throws Exception {

        List<Email> emails = emailRepository.findAllBySent(false);

        for (Email email : emails) {
            sendEmail(email);
        }

        emailRepository.saveAll(emails);
    }

    private void sendEmail(Email email) throws Exception {
        sendEmailTLS.send(
            email.getSubject(),
            email.getText(),
            email.getToEmail()
        );

        email.setSent(true);
    }
}
