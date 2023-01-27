package com.investment.simulatedInvestment.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.investment.simulatedInvestment.dto.MemberDto;
import com.investment.simulatedInvestment.entity.Member;
import com.investment.simulatedInvestment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/index")
    public @ResponseBody String index(){
        return "로그인 완료!";
    }

    @GetMapping("/")
    public String loginMember(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm() {
        return "index";
    }
    @PostMapping("/join")
    public String createMember(@ModelAttribute MemberDto form){
        System.out.println("lllllllllllllllllllllllllllll");
        System.out.println(form);
        memberService.createUser(form);
        return "redirect:/";
    }

}
