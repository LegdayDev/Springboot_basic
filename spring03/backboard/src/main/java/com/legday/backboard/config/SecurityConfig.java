package com.legday.backboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                // localhost:8080/ 로 시작하는 모든 경로 허용
                authorizeHttpRequests(auth -> auth.
                        anyRequest().permitAll()).
                // CSRF 위변조 공격을 막는 부분해제, 특정 URL은 CSRF 공격 리스트에서 제거
//                csrf(csrt -> csrt.ignoringRequestMatchers("/h2-console/**")).
                csrf(csrf->csrf.disable()).
                // h2-console 페이지가 <frameset> , <frame>으로 구성되기 때문에 CORS와 유사한 옵션 추가
                headers(headerConfig -> headerConfig.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))).
                formLogin(config -> config.loginPage("/member/login").defaultSuccessUrl("/")).
                logout(config -> config.
                        logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")).
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true))
        ;

        return http.build();
    }
}
