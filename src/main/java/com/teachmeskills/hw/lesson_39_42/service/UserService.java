package com.teachmeskills.hw.lesson_39_42.service;

import com.teachmeskills.hw.lesson_39_42.model.User;
import com.teachmeskills.hw.lesson_39_42.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> createUser(User user) {
        Optional<Long> userId = userRepository.createUser(user);
        if (userId.isPresent()) {
            return getUserById(userId.get());
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public Optional<User> updateUser(User user) {
        Boolean updated = userRepository.updateUser(user);
        if (updated) {
            return userRepository.getUserById(user.getId());
        }
        return Optional.empty();
    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
