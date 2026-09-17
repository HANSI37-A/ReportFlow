package com.example.demo.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/member/test")
    public String memberTest() {
        return "TEAM_MEMBER access granted";
    }

    @GetMapping("/manager/test")
    public String managerTest() {
        return "MANAGER access granted";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "ADMIN access granted";
    }
}