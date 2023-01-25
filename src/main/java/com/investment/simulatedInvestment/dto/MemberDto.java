package com.investment.simulatedInvestment.dto;

import com.investment.simulatedInvestment.common.Role;
import com.investment.simulatedInvestment.entity.Member;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.beans.Encoder;

@Data
@Setter(AccessLevel.NONE)
public class MemberDto {

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String username;

    private String nickname;

    private String password;

    private Role role;

    @Builder
    public MemberDto(String username, String nickname, String password, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
//bCryptPasswordEncoder.encode(password)
//    @Builder
//    public MemberDto(Member member) {
//        this.username = member.getUsername();
//        this.nickname = member.getNickname();
//        this.password = member.getPassword();
//        this.role = member.getRole();
//    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();
    }
}
