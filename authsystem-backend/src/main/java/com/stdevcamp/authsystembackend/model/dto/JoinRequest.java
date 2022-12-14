package com.stdevcamp.authsystembackend.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {

    private String email;
    private String password;
    private String name;

    public JoinRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;

    }

}
