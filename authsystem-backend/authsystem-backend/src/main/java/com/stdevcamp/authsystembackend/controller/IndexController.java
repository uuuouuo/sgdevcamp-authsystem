package com.stdevcamp.authsystembackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/management")
    public String getUserAll() {
        System.out.println("Controller !!!!!!!!!!!!!!!!");
        return "management";
    }

}
