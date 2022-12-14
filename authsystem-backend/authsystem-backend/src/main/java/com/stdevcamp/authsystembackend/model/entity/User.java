package com.stdevcamp.authsystembackend.model.entity;

import com.stdevcamp.authsystembackend.model.dto.JoinRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;

//    @Builder
//    public User(String name, String email, String picture, Role role) {
//        this.name = name;
//        this.email = email;
//        this.role = role;
//    }

    public void createUser(JoinRequest request, String password) {
        this.email = request.getId();
        this.name = request.getName();
        this.password = password;
//        this.role = Role.USER;
    }

}
