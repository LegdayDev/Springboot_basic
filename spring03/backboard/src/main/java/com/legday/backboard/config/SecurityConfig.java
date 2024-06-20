package com.legday.backboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
            // localhost:8080/ 로 시작하는 모든 경로 허용
            authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll()).
            // CSRF 위변조 공격을 막는 부분해제, 특정 URL은 CSRF 공격 리스트에서 제거
            csrf(csrt -> csrt.ignoringRequestMatchers("/h2-console/**")).
            // h2-console 페이지가 <frameset> , <frame>으로 구성되기 때문에 CORS와 유사한 옵션 추가
            headers(headerConfig -> headerConfig.addHeaderWriter(
                    new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
        ;

        return http.build();
    }
}
