package com.stdevcamp.authsystembackend.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {

    private String id;
    private String password;
    private String name;

    public JoinRequest(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;

    }

}
