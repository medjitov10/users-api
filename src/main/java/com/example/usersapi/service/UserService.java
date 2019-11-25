package com.example.usersapi.service;

import com.example.usersapi.model.Profile;
import com.example.usersapi.model.User;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface UserService {

    public List<User> listUsers();

    public List<String> signUp(User user);

    public List<String> logIn(User user);

    public Profile createProfile(Profile profile, String tokenHeader);

    public Profile getProfile(String token);

    public Profile updateProfile(Profile profile, String tokenHeader);

//    public User searchById(long id);
//
//    public Iterable<User> searchByName(String name);
//
//    public HttpStatus deleteUser(long id);
//
//    public HttpStatus createUser(User user);
//
//    public HttpStatus updateUser(long id, User userRequest);
}
