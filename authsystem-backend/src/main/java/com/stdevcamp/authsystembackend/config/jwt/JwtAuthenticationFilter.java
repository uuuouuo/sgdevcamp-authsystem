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
        final String header = request.getHeader("JWT");

        try {
            if(header != null && header.startsWith("Bearer ")
                    && jwtProvider.validateToken(header.replace("Bearer ", ""))) {
                String jwtToken = header.replace("Bearer ", "");
                Authentication authentication = jwtProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch(Exception e) {
            e.printStackTrace();
            if(header == null) {
                request.setAttribute("exception", ErrorCode.NON_LOGIN.getMessage());
            } else if(!jwtProvider.validateToken(header.replace("Bearer ", ""))) {
                request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getMessage());
            } else {
                e.printStackTrace();
                request.setAttribute("exception", ErrorCode.INVALID_TOKEN.getMessage());
            }
        }
            filterChain.doFilter(request, response);
    }
}
