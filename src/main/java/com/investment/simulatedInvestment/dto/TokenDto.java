package com.investment.simulatedInvestment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@Builder
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireIn;
    private Long refreshTokenExpiresIn;
    private String authority;
    private String info;

    public void setInfo(String info){
        this.info = info;
    }

}
