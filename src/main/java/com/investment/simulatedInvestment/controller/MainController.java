package com.investment.simulatedInvestment.controller;

import com.investment.simulatedInvestment.dto.MemberDto;
import com.investment.simulatedInvestment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String createUser(@RequestBody MemberDto form, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/";
        }
        memberService.createUser(form);

        return "redirect:/";
    }
}
