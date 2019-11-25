package com.example.usersapi.service;

import com.example.usersapi.config.JwtUtil;
import com.example.usersapi.model.Profile;
import com.example.usersapi.model.Role;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.ProfileRepository;
import com.example.usersapi.repository.RoleRepository;
import com.example.usersapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> listUsers() {
        return null;
    }

    @Override
    public List<String> signUp(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
        Role role = roleRepository.findById(1l).orElse(new Role("ROLE_USER"));

        roleRepository.save(role);

        user.addRole(role);
        if(userRepository.save(user) != null) {
            return Arrays.asList( jwtUtil.generateToken(user.getUsername()), user.getUsername());
        }
        return null;
    }

    @Override
    public List<String> logIn(User user) {
        User savedUser = userRepository.findByUsername(user.getUsername());
        if( savedUser != null && encoder().matches(user.getPassword(), savedUser.getPassword())) {
            return Arrays.asList( jwtUtil.generateToken(user.getUsername()), savedUser.getUsername());
        }
        return null;
    }


    @Override
    public Profile createProfile(Profile profile, String username) {
        User user = userRepository.findByUsername(username);
        if(user.getProfile() != null) {
            profile.setId(user.getProfile().getId());
        }
        profile.setUser(user);
        profileRepository.save(profile);
        return user.getProfile();
    }

    @Override
    public Profile getProfile(String username) {
        User user = userRepository.findByUsername(username);
        return user.getProfile();
    }

    @Override
    public Profile updateProfile(Profile profile, String tokenHeader) {
        return null;
    }

}
