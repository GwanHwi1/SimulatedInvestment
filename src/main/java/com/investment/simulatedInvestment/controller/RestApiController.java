package com.investment.simulatedInvestment.controller;

import com.investment.simulatedInvestment.config.security.CustomUserDetails;
import com.investment.simulatedInvestment.dto.MemberDto;
import com.investment.simulatedInvestment.entity.Member;
import com.investment.simulatedInvestment.repository.MemberRepository;
import com.investment.simulatedInvestment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class RestApiController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
    }

    @GetMapping("/user")
    public CustomUserDetails user(Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getUsername());
        System.out.println("principal : "+principal.getUser().getPassword());

        return principal;
    }

    // 어드민이 접근 가능
    @GetMapping("/admin/users")
    public List<Member> users(){
        return memberRepository.findAll();
    }

    @PostMapping("/join")
    public String join(@RequestBody MemberDto user) {
        memberService.createUser(user);
        return "회원가입완료";
    }

//    @GetMapping("/api/user")
//    public @ResponseBody String user(Authentication authentication){
//        CustomUserDetails custom =(CustomUserDetails) authentication.getPrincipal();
//        System.out.println("authentication: "+ custom.getUsername());
//        return "user";
//    }
//
//    @GetMapping("/api/user/a")
//    public String users(){
//        return "users";
//    }
//
//    @GetMapping("/user")
//    public CustomUserDetails userq(Authentication authentication) {
//        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
//        System.out.println("principal : "+principal.getUsername());
//        System.out.println("principal : "+principal.getPassword());
//
//        return principal;
//    }
//
//    @PostMapping("/join")
//    public String join(@RequestBody MemberDto user) {
//        memberService.createUser(user);
//        return "회원가입완료";
//    }
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user) {
//        Member member = memberRepository.findByUsername(user.get("username"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//        return jwtTokenProvider.createToken(member.getUsername(), member.getRole());
//    }



}
