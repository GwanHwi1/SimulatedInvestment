package com.investment.simulatedInvestment.service;

import com.investment.simulatedInvestment.common.Role;
import com.investment.simulatedInvestment.config.security.CustomUserDetails;
import com.investment.simulatedInvestment.config.security.jwt.TokenProvider;
import com.investment.simulatedInvestment.dto.*;
import com.investment.simulatedInvestment.entity.Member;
import com.investment.simulatedInvestment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisTemplate redisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Response response;
    private final TokenProvider tokenProvider;

    @Transactional
    public Member createUser(MemberDto dto) {
        Member encoding = Member.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();
        return memberRepository.save(encoding);
    }

    public ResponseEntity<?> login(LoginDto loginDto){
        Member member = memberRepository.findByUsername(loginDto.getUsername()).orElse(null);
        if (member == null) {
            return response.fail("ID ?????? ??????????????? ???????????????", HttpStatus.BAD_REQUEST);
        }
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenDto.getRefreshToken(),
                tokenDto.getAccessTokenExpireIn(),
                TimeUnit.MILLISECONDS);

        if (member.getRole() == Role.USER) {
            tokenDto.setInfo(member.getNickname());
        } else{
            tokenDto.setInfo(member.getRole().toString()); //admin?????? admin tokenDto??? ??????
        }

        return response.success(tokenDto, "???????????? ??????", HttpStatus.OK);
    }

    public ResponseEntity<?> reissue(TokenRequestDto reissue) {

        if (!tokenProvider.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = tokenProvider.getAuthentication(reissue.getAccessToken());

        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());

        log.info("refresh Token: {}", refreshToken);

        if (ObjectUtils.isEmpty(refreshToken)) {
            return response.fail("????????? ???????????????", HttpStatus.BAD_REQUEST);
        }
        if (!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDto.getRefreshToken(),
                        tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);

        return response.success(tokenDto, "?????? ????????? ?????????????????????.", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(TokenRequestDto logout) {
        if (!tokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("????????? ???????????????.", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = tokenProvider.getAuthentication(logout.getAccessToken());

        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            //refresh token ??????
            redisTemplate.delete("RT:" + authentication.getName());
        }

        Long expiration = tokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("???????????? ???????????????");
    }
}
