package com.simplilearn.cabbooking.Service;


import com.simplilearn.cabbooking.model.User;
import com.simplilearn.cabbooking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create or save a user
    public User saveUser(User user) {
        // Validate user data
        this.validateUser(user);
        return this.userRepository.save(user);
    }

    // Find a user by ID
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> findUserById(long userId) {
        return userRepository.findById(userId);
    }

    // Find all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Update user details
    public void updateUser(User user) {
        // Validate user data
        this.validateUser(user);

        Optional<User> existingUser = userRepository.findById(user.getUserId());

        existingUser.ifPresent(u -> {
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            u.setPhoneNumber(user.getPhoneNumber());
            u.setPassword(user.getPassword());
            u.setUsername(user.getUsername());
            u.setCreatedAt(user.getCreatedAt());

            userRepository.save(u);
        });
    }

    // Delete a user
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Validate user data
    private void validateUser(User user) {
        if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Invalid User Data: " + user);
        }
    }
}
