package com.example.learnmocktest.learn;

import com.example.learnmocktest.learn.Exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        Optional<User> user1 = userRepository.findById(user.getId());
        if (user1.isEmpty()) {
            return userRepository.save(user);
        } else {
            throw new UserException("userNotFound");
        }
    }

    public User updateUser(User user, Long userId) {
        Optional<User> user1 = userRepository.findById(userId);
        if (user1.isEmpty()) {
            throw new UserException("userNotFound");
        } else {
            user1 = Optional.ofNullable(user);
            return userRepository.save(user1.get());
        }
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserException("userNotFound");
        }else {
        userRepository.deleteById(id);
        }
    }

    public User findUser(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
