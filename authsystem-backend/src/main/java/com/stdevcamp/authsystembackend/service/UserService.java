package com.stdevcamp.authsystembackend.service;

import com.stdevcamp.authsystembackend.model.dto.JoinRequest;
import com.stdevcamp.authsystembackend.model.dto.LoginRequest;

import java.util.Map;

public interface UserService {
    Map<String, Object> join(JoinRequest request);
    String login(LoginRequest request);
    String refresh(LoginRequest request);
    Map<String, Object> findUsers();
}
