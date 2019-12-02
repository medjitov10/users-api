package com.example.usersapi.controller;

import com.example.usersapi.exception.EntityNotFoundException;
import com.example.usersapi.model.Profile;
import com.example.usersapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Profile getProfile(@RequestHeader("username") String username) {
        return userService.getProfile(username);
    }

    @PostMapping
    public Profile createProfile(@RequestHeader("username") String username, @RequestBody Profile profile) throws EntityNotFoundException {
        return userService.createProfile(profile, username);
    }

}

