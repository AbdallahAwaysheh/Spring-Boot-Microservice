package com.orange.e_shop.user_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @GetMapping("/admin")
    public String test2() {
        return "This is only for admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String test3() {
        return "This is for admin";
    }
}
