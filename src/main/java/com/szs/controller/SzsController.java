package com.szs.controller;

import com.szs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
public class SzsController {
    private final UserService userService;
    @Autowired
    public SzsController(UserService userService) {
        this.userService = userService;
    }
}
