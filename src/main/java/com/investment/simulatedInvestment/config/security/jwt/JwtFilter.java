package com.investment.simulatedInvestment.config.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_ACCESS = "accessToken";
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = resolveToken(request);

        log.info("jwt 토큰 = {}   {}", accessToken, request.getRequestURI());

        if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            /*1. Redis 에 해당 accessToken logout 여부 확인 */
            String isLogout = (String) redisTemplate.opsForValue().get(accessToken);
            if (ObjectUtils.isEmpty(isLogout)) {
                /*2. 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장 */
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    public String resolveToken(HttpServletRequest request) {
        if(request.getCookies() == null) return null;

        Cookie accessCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> AUTHORIZATION_ACCESS.equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        return accessCookie.getValue();
    }
}
