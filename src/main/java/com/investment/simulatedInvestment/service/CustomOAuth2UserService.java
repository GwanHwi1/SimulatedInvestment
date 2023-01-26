package com.investment.simulatedInvestment.service;

import com.investment.simulatedInvestment.common.Role;
import com.investment.simulatedInvestment.config.security.CustomUserDetails;
import com.investment.simulatedInvestment.dto.MemberDto;
import com.investment.simulatedInvestment.entity.Member;
import com.investment.simulatedInvestment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        MemberDto memberDto = findByOAuth2User(oAuth2User);
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);

        return new CustomUserDetails(memberDto, oAuth2User.getAttributes());
    }

    @Transactional
    public MemberDto findByOAuth2User(OAuth2User oAuth2User) {
        String username = oAuth2User.getAttribute("email");
        Member member = memberRepository.findByUsername(username)
                .orElse(createOAuth2User(oAuth2User));

        return toDto(member);
    }

    public Member createOAuth2User(OAuth2User oAuth2User) {

        Member member = MemberDto.builder()
                .username(oAuth2User.getAttribute("email"))
                .nickname(oAuth2User.getAttribute("name"))
                .password("비밀번호 뭐로하지?")
                .role(Role.USER)
                .build()
                .toEntity();

        return memberRepository.save(member);
    }

    private MemberDto toDto(Member member) {
        return MemberDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
    }


}
