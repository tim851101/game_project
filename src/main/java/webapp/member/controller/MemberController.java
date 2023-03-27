package webapp.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.MemberDTO;
import webapp.member.service.MemberService;

@Controller
@RequestMapping("/mem")
public class MemberController {

    @Autowired
    private MemberService memberServiceImpl;

    @PostMapping("/login")
    @ResponseBody
    public Boolean memberLogin(@RequestBody LoginDTO loginDTO){
        return (memberServiceImpl.memberLogin(loginDTO));
    }

    @GetMapping("/find-one")
    @ResponseBody
    public MemberDTO findMemById(@RequestParam Integer id){
        return memberServiceImpl.findById(id);
    }
    @GetMapping("/ls-one")
    @ResponseBody
    public MemberDTO findById(@RequestParam Integer id){
        return memberServiceImpl.findById(id);
    }

    @PostMapping("/save")
    @ResponseBody
    public Boolean saveMember(@RequestBody MemberDTO memberDTO){
        return memberServiceImpl.saveMember(memberDTO);
    }


    @PostMapping("/account")
    @ResponseBody
    public MemberDTO findByMemEmail(@RequestParam String email){
        return memberServiceImpl.findByMemEmail(email);
    }



}
