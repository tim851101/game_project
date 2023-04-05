package webapp.others.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.others.dto.EmailMessageDTO;
import webapp.others.service.EmailService;


@Controller
@RequestMapping("/test")
public class EmailSenderController {

    @Autowired
    private EmailService emailServiceImpl;

    @PostMapping("/send-email")
    @ResponseBody
    String sendEmailMassage(@RequestBody EmailMessageDTO emailMessageDTO) {
        if(emailMessageDTO.getName()==null || "".equals(emailMessageDTO.getName())){
            return "請輸入姓名";
        }
        if(emailMessageDTO.getFrom()==null || "".equals(emailMessageDTO.getFrom())){
            return "請輸入信箱";
        }
        if(emailMessageDTO.getSubject()==null || "".equals(emailMessageDTO.getSubject())){
            return "請輸入標題";
        }
        if(emailMessageDTO.getText()==null || "".equals(emailMessageDTO.getText())){
            return "請輸入內容";
        }
        try {
            emailServiceImpl.sendEmail(emailMessageDTO.getName(),emailMessageDTO.getFrom(),emailMessageDTO.getSubject(),emailMessageDTO.getText());
        }catch (Exception e) {
            e.printStackTrace();
            return "寄送失敗";
        }
        return "寄送成功";
    }
}
