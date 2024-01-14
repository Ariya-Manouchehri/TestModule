package com.example.learnmocktest;

import com.example.learnmocktest.learn.Exception.UserException;
import com.example.learnmocktest.learn.User;
import com.example.learnmocktest.learn.UserRepository;
import com.example.learnmocktest.learn.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    static User user;

    @BeforeAll
    static void setUp() {
        user = new User(1L, "ariya", "manouchehri", 23);
    }

    @Test
    void addUser() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Assertions.assertEquals(user, userService.addUser(user));
    }

    @Test
    void updateUser() {
        user.setName("amir");
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(userService.updateUser(user, user.getId()), user);
    }

    @Test
    void updateUserResponse500() {
        user.setName("amir");
        Mockito.when(userRepository.findById(5L)).thenThrow(UserException.class);
        //Mockito.when(userRepository.save(any())).thenThrow(UserException.class);

        Assertions.assertThrows(UserException.class, () -> userService.updateUser(any(), 5L));
    }

    @Test
    void deleteUserResponse500() {
        Mockito.when(userRepository.findById(any())).thenThrow(UserException.class);

        Assertions.assertThrows(UserException.class,() -> userService.deleteUser(any()));
    }

}
