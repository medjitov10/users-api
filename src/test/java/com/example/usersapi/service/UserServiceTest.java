package com.example.usersapi.service;

import com.example.usersapi.config.JwtUtil;
import com.example.usersapi.exception.EntityNotFoundException;
import com.example.usersapi.model.Profile;
import com.example.usersapi.model.Role;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.ProfileRepository;
import com.example.usersapi.repository.RoleRepository;
import com.example.usersapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.security.auth.login.CredentialException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    User user;
    @InjectMocks
    Role role;
    @InjectMocks
    Profile profile;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    BCryptPasswordEncoder encoder;
    @Mock
    RoleRepository roleRepository;
    @Mock
    ProfileRepository profileRepository;
    @Mock
    UserRepository userRepository;
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    @Before
    public void initializeDummyUser() {
        profile.setId(1l);
        user.setId(1L);
        user.setUsername("osman");
        user.setPassword("$2a123123");
        user.setProfile(profile);
    }

    @Test
    public void encode_UserService_Success() {
        assertNotNull(userService.encoder());
    }

    @Test
    public void listUsers_UserService_Success() {
        Iterable<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(userService.listUsers(), users);
    }

    @Test
    public void signUp_UserService_Succes() throws Exception {
//        when(encoder.encode(any())).thenReturn("123123");
        Optional<Role> roleOptional = Optional.of(role);
        when(roleRepository.findById(any())).thenReturn(roleOptional);
        when(roleRepository.save(any())).thenReturn(role);
        when(userRepository.save(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn("123456");
        assertNotNull(userService.signUp(user));
    }

    @Test
    public void logIn_UserService_Controller() throws CredentialException {
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn("123456");

        List<String> actualToken = userService.logIn(user);
        assertNotNull(actualToken);
    }
//    @Override
//    public List<String> logIn(User user) {
//        User savedUser = userRepository.findByUsername(user.getUsername());
//        if( savedUser != null && encoder().matches(user.getPassword(), savedUser.getPassword())) {
//            return Arrays.asList( jwtUtil.generateToken(user.getUsername()), savedUser.getUsername());
//        }
//        return null;
//    }
//
    @Test
    public void createProfile_userService_Success() throws EntityNotFoundException {
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(profileRepository.save(profile)).thenReturn(profile);
        assertNotNull(userService.createProfile(profile, "osman"));
    }

    @Test
    public void getProfile_userService_Success() {
        when(userRepository.findByUsername(any())).thenReturn(user);
        assertNotNull(userService.getProfile("osman"));
    }


}
