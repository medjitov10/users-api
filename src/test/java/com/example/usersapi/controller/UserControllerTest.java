package com.example.usersapi.controller;

import com.example.usersapi.model.User;
import com.example.usersapi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    UserController userController;
    @InjectMocks
    User user;

    @Mock
    UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Before
    public void createDummyUser() throws JsonProcessingException {
        user.setUsername("osman");
        jsonMapper = mapper.writeValueAsString(user);
    }

    private ObjectMapper mapper = new ObjectMapper();
    private String jsonMapper;

    @Test
    public void listUsers_UserController_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list").contentType(MediaType.APPLICATION_JSON)
                .header("username", user.getUsername())
                .content(createUserInJson());
        Iterable<User> users = new ArrayList<>();
        String listOfusersString = mapper.writeValueAsString(users);
        when(userService.listUsers()).thenReturn(users);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(listOfusersString)).andReturn();
    }

    @Test
    public void signup_UserController_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup").contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson());
        List<String> userString = Arrays.asList("123456", "user");
        when(userService.signUp(any())).thenReturn(userString);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"user\",\"token\":\"123456\"}"))
                .andReturn();
    }
    @Test
    public void login_UserController_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson());

        List<String> retString = Arrays.asList("123456", "user");

        when(userService.logIn(any())).thenReturn(retString);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"user\",\"token\":\"123456\"}"))
                .andReturn();
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        return ResponseEntity.ok(new JwtResponse(userService.logIn(user)));
//    }
    private String createUserInJson() {
        return "{\"id\":1,\"title\":\"Bye bye\",\"description\":\"hi hi\",\"user\":{\"username\":\"osman\"}}";
    }
}
