package com.stdevcamp.authsystembackend.config;

import com.stdevcamp.authsystembackend.config.jwt.JwtAuthenticationEntryPoint;
import com.stdevcamp.authsystembackend.config.jwt.JwtAuthenticationFilter;
import com.stdevcamp.authsystembackend.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.httpBasic().disable();
        http.cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/list").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                ;

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
