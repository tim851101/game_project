package webapp.others.service;

import jakarta.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    public EmailServiceImpl(MailSender mailSender, JavaMailSenderImpl javaMailSenderImpl) {
        this.mailSender = mailSender;
        this.javaMailSenderImpl = javaMailSenderImpl;
    }


    public EmailMessage receiveEmails()throws Exception {
        EmailMessage emailMessage = new EmailMessage();
        try {
            Properties props = javaMailSenderImpl.getJavaMailProperties();
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(javaMailSenderImpl.getUsername(), javaMailSenderImpl.getPassword());

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            Message message = messages[0]; // 獲取第一封郵件
            emailMessage.setName(message.getFileName());
            emailMessage.setFrom(Arrays.toString(message.getFrom()));
            emailMessage.setSubject(message.getSubject());
            emailMessage.setText(message.getContent().toString());


            inbox.close(false);
            store.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return emailMessage;
    }
    @Override
    public void sendEmail(String name, String from, String subject, String text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("my518lin@gmail.com");
        message.setFrom(from);
        System.out.println(from);
        message.setSubject(subject);
        message.setText(text);
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
