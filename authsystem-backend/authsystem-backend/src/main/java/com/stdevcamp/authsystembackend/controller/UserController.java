package com.stdevcamp.authsystembackend.controller;

import com.stdevcamp.authsystembackend.config.jwt.JwtProperties;
import com.stdevcamp.authsystembackend.exception.Message;
import com.stdevcamp.authsystembackend.model.dto.JoinRequest;
import com.stdevcamp.authsystembackend.model.dto.LoginRequest;
import com.stdevcamp.authsystembackend.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping(value = "/join")
    public ResponseEntity<Map<String, Object>>  createUser(@RequestBody JoinRequest request) {
        System.out.println("======>>> Join Success !");

        return ResponseEntity.ok().body(userService.join(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginRequest request, HttpServletResponse http) {
        System.out.println(request.getId()+" "+request.getPassword());

        String jwtToken = userService.login(request);
        http.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        Map<String, Object> response = new HashMap<>();
        response.put("message", Message.TOKEN_CREAT_SUCCESS_MESSAGE);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<Map<String, Object>> updateToken(@RequestBody LoginRequest request, HttpServletResponse http) {

        String refresh = userService.refresh(request);

        http.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + refresh);

        Map<String, Object> response = new HashMap<>();
        response.put("message", Message.TOKEN_CREAT_SUCCESS_MESSAGE);

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getUserAll() {
        return ResponseEntity.ok().body(userService.findUsers());
    }


}
