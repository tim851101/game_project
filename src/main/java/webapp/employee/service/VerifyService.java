package webapp.employee.service;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import webapp.employee.repository.VerifyCodeRepository;

@Service
public class VerifyService {

    final private VerifyCodeRepository verifyCodeRepository;

    final private JavaMailSender mailSender;

    @Autowired
    public VerifyService(VerifyCodeRepository verifyCodeRepository, JavaMailSender mailSender) {
        this.verifyCodeRepository = verifyCodeRepository;
        this.mailSender = mailSender;
    }

    public Boolean saveVerifyCode(String email, String code) {
        try {
            verifyCodeRepository.saveVerifyCode(email, code);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer findVerifyCode(String email) {
        try {
            return Integer.parseInt(verifyCodeRepository.findVerifyCode(email));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Boolean sendAndStoreVerify(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = String.valueOf(generateRandomNumber());
        try {
            message.setFrom("boardgame@mail.com");
            message.setTo("public92817@gmail.com"); // should be email
            message.setSubject("遊 game 驗證碼");
            message.setText("您的驗證碼為: " + code);
            mailSender.send(message);
            saveVerifyCode(email, code);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return randomNumber;
    }
}
