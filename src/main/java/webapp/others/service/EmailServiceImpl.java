package webapp.others.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
                "系統信件通知：\n\n" +
                        "您好" + name + "先生/小姐:\n" +
                        "感謝您對遊Game團隊舉辦的賽事 " + eventName + " 的關注和支持\n" +
                        "我們很抱歉地的告知您所預約的賽事" + text + "而取消。 \n" +
                        "我們深切理解這會帶給您的不便和失望，請接受我們的歉意。\n\n" +
                        "\n" +
                        "我們非常重視您的需求和反饋，如果您有任何其他問題或疑慮，請不要猶豫，隨時聯繫我們的官方客服系統。\n" +
                        "我們的專業團隊會盡力為您提供最好的幫助和支持。\n" +
                        "再次感謝您的支持和理解，期待能在未來的活動中再次與您見面。\n" +
                        "祝您一切順利！\n" +
                        "\n" +
                        "\n" +
                        "                                    遊Game團隊");
        this.mailSender.send(sendCancelEvent);

    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
