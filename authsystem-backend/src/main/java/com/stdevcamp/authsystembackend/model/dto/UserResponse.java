package com.stdevcamp.authsystembackend.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;

    public UserResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
