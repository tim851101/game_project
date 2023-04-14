package webapp.others.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class EmailServiceImpl implements EmailService {

    private final MailSender mailSender;

    public EmailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String name, String from, String subject, String text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("my518lin@gmail.com");
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);

        this.mailSender.send(message);
    }

    @Override
    public void receiveEmails(String name, String to, String subject, String text) throws MessagingException {

    }

    @Override
    public void sendPassword(String to, String text) throws MessagingException {
        SimpleMailMessage sendPasswod = new SimpleMailMessage();
        sendPasswod.setFrom("my518lin@gmail.com");
        sendPasswod.setTo(to);
        sendPasswod.setSubject("您的臨時登入密碼");
        sendPasswod.setText("登入後請至會員中心變更密碼\n" + "您的臨時密碼為" + text);
        this.mailSender.send(sendPasswod);
    }
    // @Override
    // public void sendSimpleEmail(String to, String text) {
    // SimpleMailMessage message = new SimpleMailMessage();
    // message.setFrom("my518lin@gmail.com");
    // message.setTo(to);
    // message.setSubject("您的臨時登入密碼");
    // message.setText("登入後請至會員中心變更密碼\n"+"您的臨時密碼為"+text);
    // mailSender.send(message);
    // }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
