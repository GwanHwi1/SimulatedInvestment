package com.investment.simulatedInvestment.dto;

import com.investment.simulatedInvestment.common.Role;
import com.investment.simulatedInvestment.entity.Member;
import lombok.*;

@Data
@Setter(AccessLevel.NONE)
public class MemberDto {

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

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();
    }
}
