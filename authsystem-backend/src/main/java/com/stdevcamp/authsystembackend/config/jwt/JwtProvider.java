package com.stdevcamp.authsystembackend.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stdevcamp.authsystembackend.model.entity.User;
import com.stdevcamp.authsystembackend.repository.UserRepository;
import com.stdevcamp.authsystembackend.service.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 토큰을 생성하고 검증하는 클래스
 * 해당 컴포넌트는 필터클래스에서 사전 검증을 거침
 */

//@Component
@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final PrincipalDetailService principalDetailService;
    private final UserRepository userRepository;

    // JWT 토큰 생성
    public String createToken(User user) {
        String jwtToken = JWT.create()
                .withSubject("JWT토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()
                        + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }

    // JWT 토큰에서 사용자 정보 조회
    public Authentication getAuthentication(String jwtToken) {
        UserDetails principalDetails
                = principalDetailService.loadUserByUsername(this.getUserId(jwtToken));
        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }

    // JWT 토큰에서 회원 정보 추출
    public String getUserId(String jwtToken) {
        String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build().verify(jwtToken).getClaim("id").asString();
//        System.out.println("=========>>> id "+id);
        return id;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build().verify(jwtToken);
        return verify.getExpiresAt().after(new Date());
    }
}
