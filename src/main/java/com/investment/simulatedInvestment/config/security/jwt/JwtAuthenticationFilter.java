package com.investment.simulatedInvestment.config.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.simulatedInvestment.config.security.CustomUserDetails;
import com.investment.simulatedInvestment.dto.LoginDto;
import com.investment.simulatedInvestment.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("로그인중");
        ObjectMapper om = new ObjectMapper();
        LoginDto loginDto = null;

        try {
            loginDto = om.readValue(request.getInputStream(), LoginDto.class);

//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

//            Authentication authentication =
//                    authenticationManager.authenticate(authenticationToken);
//
//            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//            System.out.println("로그인 완료됨 ?:" + customUserDetails.getUsername());

//            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return null;
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword());

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        CustomUserDetails principalDetailis = (CustomUserDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetailis.getUser().getUsername());
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨: 인증이 완료");
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        System.out.println(customUserDetails);

        String jwtToken = JWT.create()
                .withSubject(customUserDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) //만료시간 10분
                .withClaim("username", customUserDetails.getUser().getUsername())
                .withClaim("nickname", customUserDetails.getUser().getNickname())
                .sign(Algorithm.HMAC512("Gwan"));
        response.addHeader("Authorization", "Bearer " + jwtToken);
        System.out.println("토큰 생성 완료");
    }
}