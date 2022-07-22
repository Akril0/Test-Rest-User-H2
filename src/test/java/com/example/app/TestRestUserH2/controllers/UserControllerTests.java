package com.example.app.TestRestUserH2.controllers;

import com.example.app.TestRestUserH2.entity.User;
import com.example.app.TestRestUserH2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception {

        User user = User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();
        given(userService.saveUser(any(User.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(user.getName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())))
                .andExpect(jsonPath("$.phone",
                        is(user.getPhone())));

    }

    @Test
    public void givenListOfUsers_whenGetAllUsers_thenReturnUsersList() throws Exception {
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build());

        listOfUsers.add(User.builder()
                .name("Samantha")
                .email("alexbatin2000@gmail.com")
                .phone("555 422-7703")
                .build());

        given(userService.getAllUser()).willReturn(listOfUsers);

        ResultActions response = mockMvc.perform(get("/api/users"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfUsers.size())));

    }

    @Test
    public void givenUserId_whenGetUserById_thenReturnUserObject() throws Exception {
        int employeeId = 1;
        User user = User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();
        given(userService.getUserById(employeeId)).willReturn(Optional.of(user));

        ResultActions response = mockMvc.perform(get("/api/users/{id}", employeeId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name",
                        is(user.getName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())))
                .andExpect(jsonPath("$.phone",
                        is(user.getPhone())));

    }

    @Test
    public void givenInvalidUserId_whenGetUserById_thenReturnEmpty() throws Exception {
        int employeeId = 1;
        User user = User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();
        given(userService.getUserById(employeeId)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/users/{id}", employeeId));

        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenUpdatedUser_whenUpdateUser_thenReturnUpdateUserObject() throws Exception {
        int employeeId = 1;
        User savedUser = User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();

        User updatedUser = User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();
        given(userService.getUserById(employeeId)).willReturn(Optional.of(savedUser));
        given(userService.updateUser(any(User.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/users/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name",
                        is(updatedUser.getName())))
                .andExpect(jsonPath("$.email",
                        is(updatedUser.getEmail())))
                .andExpect(jsonPath("$.phone",
                        is(updatedUser.getPhone())));
    }

    @Test
    public void givenUpdatedUser_whenUpdateUser_thenReturn404() throws Exception {
        int employeeId = 1;
        User savedUser = User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();

        User updatedUser = User.builder()
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();
        given(userService.getUserById(employeeId)).willReturn(Optional.empty());
        given(userService.updateUser(any(User.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/users/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception {
        int employeeId = 1;
        willDoNothing().given(userService).deleteUser(employeeId);

        ResultActions response = mockMvc.perform(delete("/api/users/{id}", employeeId));

        response.andExpect(status().isOk())
                .andDo(print());
    }

}