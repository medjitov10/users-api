package com.example.usersapi.controller;


import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Iterable<User> listUsers(@RequestHeader("username") String username) {
        return userService.listUsers();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(new JwtResponse(userService.signUp(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws CredentialException {
        return ResponseEntity.ok(new JwtResponse(userService.logIn(user)));
    }
}
