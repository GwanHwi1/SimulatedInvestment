package com.investment.simulatedInvestment.controller;

import com.investment.simulatedInvestment.dto.MemberDto;
import com.investment.simulatedInvestment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String createMemberForm(){
        return "index";
    }

    @PostMapping("/")
    public String createMember(@ModelAttribute MemberDto form){
//        , BindingResult result
//        if(result.hasErrors()){
//            return "index";
//        }
        memberService.createUser(form);


        return "redirect:/";
    }

}
