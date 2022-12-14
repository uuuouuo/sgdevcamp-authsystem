package com.stdevcamp.authsystembackend.controller;

import com.stdevcamp.authsystembackend.config.jwt.JwtProvider;
import com.stdevcamp.authsystembackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @PostMapping(value = "/signup")
    public void  createUser(@RequestBody SignUpReq signUpReq) {

        userService.signUp(signUpReq);

    }
}
