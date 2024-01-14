package com.example.learnmocktest.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/test")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/updateUser/{userId}")
    public User updateUser(@RequestBody User user, @RequestParam Long userId) {
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/findUser/{UserId}")
    public User findUser(@RequestParam Long userId) {
        return userService.findUser(userId);
    }

    @GetMapping("/findAllUsers")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }
}
