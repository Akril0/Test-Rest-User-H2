package com.example.app.TestRestUserH2.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.app.TestRestUserH2.entity.User;
import com.example.app.TestRestUserH2.exceptions.UserException;
import com.example.app.TestRestUserH2.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setup() {

        user = User.builder()
                .id(1)
                .name("George")
                .email("alexbatin2000@gmail.com")
                .phone("555 321-9876")
                .build();
    }

    @DisplayName("Test for save")
    @Test
    public void givenUserObject_whenSaveUser_thenReturnUserObject() throws UserException {
        given(userRepository.findByPhone(user.getPhone()))
                .willReturn(Optional.empty());

        given(userRepository.save(user)).willReturn(user);

        System.out.println(userRepository);
        System.out.println(userService);

        User savedUser = userService.saveUser(user);

        System.out.println(savedUser);

        assertThat(savedUser).isNotNull();
    }

    @DisplayName("Test for save with throws exception")
    @Test
    public void givenExistingPhone_whenSaveUser_thenThrowsException() {
        given(userRepository.findByPhone(user.getPhone()))
                .willReturn(Optional.of(user));

        System.out.println(userRepository);
        System.out.println(userService);

        Assertions.assertThrows(UserException.class, () ->
                userService.saveUser(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Test for getAllUser")
    @Test
    public void givenUsersList_whenGetAllUsers_thenReturnUsersList() {

        User user1 = User.builder()
                .id(2)
                .name("Tom")
                .email("alexbatin2000@gmail.com")
                .phone("555 125-8822")
                .build();

        given(userRepository.findAll()).willReturn(List.of(user, user1));

        List<User> userList = userService.getAllUser();

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
    }

    @DisplayName("Test for getAllUsers (negative scenario)")
    @Test
    public void givenEmptyUsersList_whenGetAllUsers_thenReturnEmptyUsersList() {

        User user1 = User.builder()
                .id(2)
                .name("Tom")
                .email("alexbatin2000@gmail.com")
                .phone("555 125-8822")
                .build();

        given(userRepository.findAll()).willReturn(Collections.emptyList());

        List<User> userList = userService.getAllUser();

        assertThat(userList).isEmpty();
        assertThat(0).isEqualTo(0);
    }

    @DisplayName("Test for getUserById")
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserObject() {
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        User savedUser = userService.getUserById(user.getId()).orElseThrow();

        assertThat(savedUser).isNotNull();

    }

    @DisplayName("Test for updateUser")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        given(userRepository.save(user)).willReturn(user);
        user.setPhone("555 999-1259");
        user.setName("Alice");
        User updatedUser = userService.updateUser(user);

        assertThat(updatedUser.getPhone()).isEqualTo("555 999-1259");
        assertThat(updatedUser.getName()).isEqualTo("Alice");
    }

    @DisplayName("Test for deleteUser")
    @Test
    public void givenUserId_whenDeleteUser_thenNothing() {
        int employeeId = 1;

        willDoNothing().given(userRepository).deleteById(employeeId);

        userService.deleteUser(employeeId);

        verify(userRepository, times(1)).deleteById(employeeId);
    }
}
