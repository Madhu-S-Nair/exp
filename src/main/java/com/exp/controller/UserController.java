package com.exp.controller;

import com.exp.entity.Users;
import com.exp.repository.UserRepository;
import com.exp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){

        return userService.verify(user);


    }
}
