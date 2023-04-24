package webapp.newbooking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.newbooking.repository.messageRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/message")
public class messageController {
    @Autowired
    messageRepository messageRepository;
    @PostMapping("/update")
    @ResponseBody
    public Boolean updatemssages(String message,Integer id){
        System.out.println(messageRepository.findByID(id));
        List<String> messages= messageRepository.findByID(id);
        if(messages==null) {
        messages=new ArrayList<String>();
        }
        messages.add(message);
        System.out.println(messages);
       return messageRepository.save(id,messages);
    }
    @PostMapping("/delete")
    @ResponseBody
    public Boolean deletemessages(@RequestParam Integer id){
        return messageRepository.deleteByID(id);
    }
}
