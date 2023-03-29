package webapp.others.service;

import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    void sendEmail(String name,String to,String subject,String text) throws Exception;
}
