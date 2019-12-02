package com.example.usersapi.controller;

import com.example.usersapi.model.Profile;
import com.example.usersapi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.ws.rs.core.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {
    private MockMvc mockMvc;
    private String token = "123456";

    @InjectMocks
    private ProfileController profileController;

    @InjectMocks
    private Profile profile;

    @Mock
    private UserService userService;

    private String username = "osman";
    ObjectMapper mapper = new ObjectMapper();
    String jsonMapper;

    @Test
    public void createProfile_ProfileController_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/profile").contentType(MediaType.APPLICATION_JSON)
                .header("username", username).content(createProfileInJson());

        when(userService.createProfile(any(), any())).thenReturn(profile);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(jsonMapper));
    }


    @Test
    public void getProfile_ProfileController_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/profile").contentType(MediaType.APPLICATION_JSON)
                .header("username", username);

        when(userService.getProfile(username)).thenReturn(profile);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(jsonMapper))
                .andReturn();;
    }

    @Before
    public void init() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Before
    public void initProfile() throws JsonProcessingException {

        profile.setId(1L);
        profile.setMobile("123213");
        jsonMapper = mapper.writeValueAsString(profile);
    }

    private static String createProfileInJson() {
        return "{\"id\":1,\"user\":null,\"additionalEmail\":null,\"mobile\":null,\"address\":null}";
    }
}
