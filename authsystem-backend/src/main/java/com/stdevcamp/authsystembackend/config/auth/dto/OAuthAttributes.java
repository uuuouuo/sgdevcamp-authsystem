package com.stdevcamp.authsystembackend.config.auth.dto;

import com.stdevcamp.authsystembackend.model.Role;
import com.stdevcamp.authsystembackend.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
//    private String registrationId; // 서비스 구분
    private String nameAttributeKey; // key가 되는 필드 값 (구글의 경우 기본적으로 제공)
    private String email;
    private String name;

    public static OAuthAttributes of(Map<String, Object> attributes, String userNameAttributeName) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .attributes(attributes)
                .build();
    }

    /**
     - User Entity 생성
     - 처음 가입하는 경우 생성
     - 기본 권한은 일반회원
     */
    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
