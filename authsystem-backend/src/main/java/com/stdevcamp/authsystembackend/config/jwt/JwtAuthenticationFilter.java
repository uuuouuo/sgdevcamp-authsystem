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
        final String jwt = request.getHeader("JWT");

        if(jwt == null) {
            request.setAttribute("exception", ErrorCode.NON_LOGIN.getCode());
            filterChain.doFilter(request,response);
        }

        if(jwtProvider.validateToken(jwt)) {
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getCode());
            filterChain.doFilter(request,response);
        }

        // 토큰이 유효한 경우 유저 정보 가져오기
        String userId = jwtProvider.getUserId(jwt);

        try {
            Authentication authentication = jwtProvider.getAuthentication(userId); // id 인증
            SecurityContextHolder.getContext().setAuthentication(authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
            filterChain.doFilter(request, response);
        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN.getCode());
            filterChain.doFilter(request, response);
        }
    }
}
