package com.stdevcamp.authsystembackend.config.auth.dto;

import com.stdevcamp.authsystembackend.model.User;

import java.io.Serializable;

/**
 - 세션에 사용자 정보를 저장하기 위한 DTO 클래스
 - User 클래스를 그대로 사용하면, User 클래스는 엔티티이기 때문에 언제 다른 엔티티와 관계가 형성될 수 있음
 - User 클래스에 직렬화 코드를 넣으면 직렬화 대상에 자식들까지 포함되어 성능 이슈, 부수 효과가 발생할 확률 높아짐

 => 직렬화 기능을 가진 세션 DTO를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움됨
 */

public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
