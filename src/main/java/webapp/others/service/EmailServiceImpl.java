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

    @Override
    public void sendCancelEvent(String name, String to, String eventName, String text) throws MessagingException {
                SimpleMailMessage sendCancelEvent = new SimpleMailMessage();
        sendCancelEvent.setTo(to);
        sendCancelEvent.setSubject("遊Game： " + eventName + " 賽事已取消");
        sendCancelEvent.setText(
                "系統信件回覆：\n\n" +
                        "您好 " + name + "，\n" +
                        "您所預約的賽事" + text + "而取消， \n" +
                        "造成您的不便敬請見諒。\n\n" +
                        "\n" +
                        "\n" +
                        "如果有其他問題請至官網客服系統填寫資料，\n" +
                        "請勿直接回覆此信件。\n\n" +
                        "\n" +
                        "\n" +
                        "                                 遊Game團隊敬上");
        this.mailSender.send(sendCancelEvent);

    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
