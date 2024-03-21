package com.sun.timelyChat.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";

    }
    @GetMapping("/secured")
    public String secured() {
        return "secured";
    }
        
}
