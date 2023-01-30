package com.investment.simulatedInvestment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class LoginDto {

    private String username;
    private String password;

}
