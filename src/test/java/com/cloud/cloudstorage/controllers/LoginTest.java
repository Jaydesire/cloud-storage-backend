package com.cloud.cloudstorage.controllers;

import com.cloud.cloudstorage.models.JwtRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void successfulLogin() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin("root");
        jwtRequest.setPassword("root");
        ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(jwtRequest);
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());
    }

    @Test
    void badRequestLogin() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin("root");
        jwtRequest.setPassword("root1");
        ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(jwtRequest);
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(username = "root", password = "root")
    void authorizedRequestList() throws Exception {
        mockMvc.perform(get("/list?limit=1")).andExpect(status().isOk());
    }

    @Test
    void unauthorizedRequestList() throws Exception {
        mockMvc.perform(get("/list")).andExpect(status().isUnauthorized());
    }


}