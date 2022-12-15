package com.stdevcamp.authsystembackend.controller;

import com.stdevcamp.authsystembackend.config.jwt.JwtProperties;
import com.stdevcamp.authsystembackend.model.dto.JoinRequest;
import com.stdevcamp.authsystembackend.model.dto.LoginRequest;
import com.stdevcamp.authsystembackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/join")
    public void  createUser(@RequestBody JoinRequest request) {
        System.out.println("======>>> Join Success !");
        // 같은 이메일 있는지 확인 필요
        userService.join(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request, HttpServletResponse http) {
        String jwtToken = userService.login(request);

        http.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
//        System.out.println("======>>> JWT ::: " + jwtToken);
        return ResponseEntity.ok().body(jwtToken);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<String> updateToken(@RequestBody LoginRequest loginReq, HttpServletResponse response) {

        String jwtToken = userService.refresh(loginReq.getId());

        response.addHeader(JwtProperties.HEADER_STRING,
                JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().body("JWT 재생성 완료");

    }

//    @GetMapping("/management")
//    public ResponseEntity<List<String>> getUserAll() {
//        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
//    }


}
