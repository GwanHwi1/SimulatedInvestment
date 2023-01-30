package com.investment.simulatedInvestment.controller;

import com.investment.simulatedInvestment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login_page() {
        return "loginForm";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/myprofile")
    public String myprofile() {
        return "myprofile";
    }

    @GetMapping("/userInfo")
    public String userInfo() {
        return "userInfo";
    }
}

//    @GetMapping("/index")
//    public @ResponseBody String index(){
//        return "로그인 완료!";
//    }
//
//    @GetMapping("/login")
//    public String loginMember(){
//        return "loginForm";
//    }
//
//    @GetMapping("/joinForm")
//    public String joinForm() {
//        return "join";
//    }

//    @PostMapping("/join")
//    public String createMember(@RequestBody MemberDto form){
//        memberService.createUser(form);
//        return "redirect:/login";
//    }

//}
