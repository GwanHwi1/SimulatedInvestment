package com.investment.simulatedInvestment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@Setter(AccessLevel.NONE)
public class LoginDto {

    private String username;
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }

}
