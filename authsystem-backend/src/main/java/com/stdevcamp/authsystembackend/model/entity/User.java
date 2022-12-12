package com.stdevcamp.authsystembackend.model.entity;

import com.stdevcamp.authsystembackend.model.Role;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class User {

    @Id
    @Column(name = "USER_ID")
    private String email;

//    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
