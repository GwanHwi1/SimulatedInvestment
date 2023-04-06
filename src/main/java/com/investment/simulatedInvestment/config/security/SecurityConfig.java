package com.investment.simulatedInvestment.config.security;

import com.investment.simulatedInvestment.config.security.jwt.JwtSecurityConfig;
import com.investment.simulatedInvestment.config.security.jwt.TokenProvider;
import com.investment.simulatedInvestment.handler.CustomAccessDeniedHandler;
import com.investment.simulatedInvestment.handler.CustomAuthenticationEntryPoint;
import com.investment.simulatedInvestment.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final TokenProvider tokenProvider;

    private final RedisTemplate redisTemplate;

    private final CorsFilter corsFilter;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**",
                        "/lib/**", "/favicon.ico", "/h2-console/**", "/errorPage");
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.csrf().disable()
                 .addFilter(corsFilter)
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .formLogin().disable()
                 .httpBasic().disable()
                 .authorizeRequests()
                 .antMatchers("/api/v1/user/**","/chat/**")
                 .access("hasRole('USER') or hasRole('ADMIN')")
                 .antMatchers("/api/v1/admin/**")
                 .access("hasRole('ADMIN')")
                 .anyRequest().permitAll()
                 .and()
                 .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                 .authenticationEntryPoint(authenticationEntryPoint)
                 .and()
                 .apply(new JwtSecurityConfig(tokenProvider, redisTemplate));
        return http.build();

    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


}