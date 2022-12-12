package com.stdevcamp.authsystembackend.config.auth.provider;

import com.stdevcamp.authsystembackend.model.Role;
import com.stdevcamp.authsystembackend.model.entity.User;
import lombok.Builder;

import java.util.Map;

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

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
