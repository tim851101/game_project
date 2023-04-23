package webapp.others.service;

import jakarta.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import webapp.others.dto.EmailMessageDTO;
import webapp.others.pojo.EmailMessage;

import java.util.Arrays;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private final MailSender mailSender;


    @Autowired
    public EmailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String name, String from, String subject, String text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("a81194lin@gmail.com");
        message.setFrom(from);
        System.out.println(from);
        message.setSubject("客服信件");
        String content = "Email："+from+"\n"+"詢問主題："+subject+"\n詢問事項："+text+"\n";
        System.out.println("This is content : "+content);
        message.setText(content);
        this.mailSender.send(message);
    }

    @Override
    public EmailMessageDTO receiveEmails(String name, String to, String subject, String text) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        return null;
    }

    @Override
    public void sendPassword(String to, String text) throws MessagingException {
        SimpleMailMessage sendPasswod = new SimpleMailMessage();
        sendPasswod.setFrom("a81194lin@gmail.com");
        sendPasswod.setTo(to);
        sendPasswod.setSubject("您的臨時登入密碼");
        sendPasswod.setText("登入後請至會員中心變更密碼\n" + "您的臨時密碼為" + text);
        this.mailSender.send(sendPasswod);
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
