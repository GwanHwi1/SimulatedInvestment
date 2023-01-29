package com.investment.simulatedInvestment.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.investment.simulatedInvestment.config.security.CustomUserDetails;
import com.investment.simulatedInvestment.dto.MemberDto;
import com.investment.simulatedInvestment.entity.Member;
import com.investment.simulatedInvestment.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);


        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        System.out.println("jwtHeader: " + jwtHeader);

        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String username =
                JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();

        if(username != null) {
            System.out.println("username 정상");
            Member userEntity = memberRepository.findByUsername(username).orElse(null);

            System.out.println("userEntity: " + userEntity.getUsername());
            System.out.println("Role: "+userEntity.getRole().toString());
            MemberDto dto = MemberDto.builder()
                    .username(userEntity.getUsername())
                    .nickname(userEntity.getNickname())
                    .password(userEntity.getPassword())
                    .role(userEntity.getRole())
                    .build();

            CustomUserDetails customUserDetails = new CustomUserDetails(dto);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("==============================================================");
        }

        chain.doFilter(request, response);
    }
}
