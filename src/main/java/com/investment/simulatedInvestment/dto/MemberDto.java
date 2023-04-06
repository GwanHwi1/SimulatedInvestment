package com.investment.simulatedInvestment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public MemberDto(@JsonProperty("username") String username,
                     @JsonProperty("nickname") String nickname,
                     @JsonProperty("password") String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

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
