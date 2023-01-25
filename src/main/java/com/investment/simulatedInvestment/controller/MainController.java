package com.investment.simulatedInvestment.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.plaf.synth.SynthOptionPaneUI;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;


    @GetMapping("/")
    public String createMemberForm(){
        System.out.println("zzzzzzzzzzzzzzzzzzzz");

        return "index";
    }

    @PostMapping("/")
    public String createMember(@ModelAttribute MemberDto form){
//        , BindingResult result
//        if(result.hasErrors()){
//            return "index";
//        }
        System.out.println("aaaaaaaaaaaaaaaaaaaaa");
        System.out.println(form);
        memberService.createUser(form);


        return "redirect:/";
    }

}
