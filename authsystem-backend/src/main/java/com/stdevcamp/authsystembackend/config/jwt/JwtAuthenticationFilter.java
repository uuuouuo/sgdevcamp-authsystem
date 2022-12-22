package com.stdevcamp.authsystembackend.config.jwt;

import com.stdevcamp.authsystembackend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter: 요청이 들어올 때 마다 JWT 인증
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Request의 Header에서 token 값을 가져옴 => "JWT" : "TOKEN값"
        final String header = request.getHeader("JWT");
        System.out.println("======> jwtToken : "+header);

        try {
            if(header != null && header.startsWith("Bearer ")
                    && jwtProvider.validateToken(header.replace("Bearer ", ""))) {
                System.out.println("=========>>> Auth Success !!!");
                String jwtToken = header.replace("Bearer ", "");
                // 토큰이 유효한 경우 유저 정보 가져오기
                Authentication authentication = jwtProvider.getAuthentication(jwtToken); // id 인증
                SecurityContextHolder.getContext().setAuthentication(authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("=========>>> Auth Fail ...");
            if(header == null) {
                request.setAttribute("exception", ErrorCode.NON_LOGIN.getMessage());
                System.out.println("=========>>> null");
            } else if(!jwtProvider.validateToken(header.replace("Bearer ", ""))) {
                request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getMessage());
                System.out.println("=========>>> expired");
            } else {
                e.printStackTrace();
                request.setAttribute("exception", ErrorCode.INVALID_TOKEN.getMessage());
                System.out.println("=========>>> invalid");
            }
        }
            filterChain.doFilter(request, response);
    }
}
