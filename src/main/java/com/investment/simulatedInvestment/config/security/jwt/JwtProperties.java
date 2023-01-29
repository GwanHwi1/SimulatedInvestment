package com.investment.simulatedInvestment.config.security.jwt;

public interface JwtProperties {
    String SECRET = "Gwan"; // 서버만 알고 있는 개인키
    
    int EXPIRATION_TIME = 60000 * 10; // 10분
    
    String TOKEN_PREFIX = "Bearer ";
    
    String HEADER_STRING = "Authorization";
}
