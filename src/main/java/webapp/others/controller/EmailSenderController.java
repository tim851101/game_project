package webapp.others.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.others.dto.EmailMessageDTO;
import webapp.others.service.EmailService;

@RestController
@RequestMapping("/test")
public class EmailSenderController {

    @Autowired
    private EmailService emailServiceImpl;

    @PostMapping("/send-email")
    @ResponseBody
    String sendEmailMassage(@RequestBody EmailMessageDTO emailMessageDTO) {
        if (emailMessageDTO.getName() == null || "".equals(emailMessageDTO.getName())) {
            return "請輸入姓名";
        }
        if (emailMessageDTO.getFrom() == null || "".equals(emailMessageDTO.getFrom())) {
            return "請輸入信箱";
        }
        if (emailMessageDTO.getSubject() == null || "".equals(emailMessageDTO.getSubject())) {
            return "請輸入標題";
        }
        if (emailMessageDTO.getText() == null || "".equals(emailMessageDTO.getText())) {
            return "請輸入內容";
        }
        try {
            emailServiceImpl.sendEmail(emailMessageDTO.getName(), emailMessageDTO.getFrom(),
                    emailMessageDTO.getSubject(), emailMessageDTO.getText());
        } catch (Exception e) {
            e.printStackTrace();
            return "寄送失敗";
        }
        return "寄送成功";
    }



    // 寄送取消賽事通知給會員
    @PostMapping("/cancelEvent")
    @ResponseBody
    String sendCancelEvent(@RequestBody EmailMessageDTO emailMessageDTO) throws MessagingException {
      try {
          emailServiceImpl.sendCancelEvent(emailMessageDTO.getName(), emailMessageDTO.getFrom(), emailMessageDTO.getSubject(), emailMessageDTO.getText());
          return "寄送成功";
      }catch (Exception e){
          e.printStackTrace();
          return "寄送失敗";

      }
    }
    @PostMapping("/emails")
    public EmailMessageDTO getEmail(@RequestBody EmailMessageDTO emailMessageDTO) throws MessagingException {

        return emailServiceImpl.receiveEmails(emailMessageDTO.getName(),emailMessageDTO.getFrom(),emailMessageDTO.getSubject(),emailMessageDTO.getText());
    }

//    @PostMapping("/test")
//    String sendEmailtoMember() throws MessagingException {
//        emailServiceImpl.sendPassword("a81194@icloud.com", "password");
//        return "123";
//    }
}
