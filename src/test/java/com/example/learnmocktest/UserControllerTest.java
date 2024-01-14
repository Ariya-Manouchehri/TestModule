package com.example.learnmocktest;

import com.example.learnmocktest.learn.Exception.UserException;
import com.example.learnmocktest.learn.User;
import com.example.learnmocktest.learn.UserController;
import com.example.learnmocktest.learn.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User input;

    private User output;

    @BeforeEach
    void setUp() {
        input = new User(1L, "ariya", "manouchehri", 21);
        output = new User(1L, "ariya", "manouchehri", 21);
    }

    @Test
    void addUserResponse200() throws Exception {
        Mockito.when(userService.addUser(input)).thenReturn(output);
        mockMvc.perform(post("/test/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void addUserResponse500() throws Exception {
        Mockito.when(userService.addUser(any())).thenThrow(UserException.class);
        mockMvc.perform(post("/test/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserResponse200() throws Exception {
        input.setName("sara");
        output.setName("sara");

        Mockito.when(userService.updateUser(input, input.getId())).thenReturn(output);

        mockMvc.perform(put("/test/updateUser/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(jsonPath("$.name").value("sara"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateUserResponse500() throws Exception {
        input.setName("sara");
        output.setName("sara");

        Mockito.when(userService.updateUser(any(), any())).thenThrow(UserException.class);

        mockMvc.perform(put("/test/updateUser/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserResponse200() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);
        mockMvc.perform(delete("/test/deleteUser/{userId}", 1))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteUserResponse500() throws Exception {
        //Mockito.doThrow(UserException.class).when(userService).findUser(1L);
        Mockito.doThrow(UserException.class).when(userService).deleteUser(5L);

        mockMvc.perform(delete("/test/deleteUser/{userId}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findUserResponse200() throws Exception {
        Mockito.when(userService.findUser(1L)).thenReturn(output);

        mockMvc.perform(get("/test/findUser")
                .param("userId", String.valueOf(output.getId())) // Pass userId as a request parameter
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findUserResponse500() throws Exception {
        Mockito.when(userService.findUser(1L)).thenThrow(UserException.class);

        mockMvc.perform(get("/test/findUser")
                        .param("userId", String.valueOf(output.getId())) // Pass userId as a request parameter
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
