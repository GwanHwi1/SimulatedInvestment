//package com.investment.simulatedInvestment.controller;
//
//import com.investment.simulatedInvestment.exception.CustomException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//@Slf4j
//public class ExceptionController {
//
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<String> except(CustomException e) {
//        log.info(e.getMessage());
//        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getMessage());
//    }
//
//}
