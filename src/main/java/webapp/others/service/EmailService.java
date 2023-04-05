package webapp.others.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    void sendEmail(String name, String to, String subject, String text) throws Exception;

    void receiveEmails(String name, String to, String subject, String text) throws MessagingException;
}