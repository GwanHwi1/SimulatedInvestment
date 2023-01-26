package com.investment.simulatedInvestment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionPageController {

    @GetMapping("/errorPage")
    public String errorPage(){
        return "error/error";
    }

}
