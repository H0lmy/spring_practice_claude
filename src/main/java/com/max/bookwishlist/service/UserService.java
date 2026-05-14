package com.max.bookwishlist.service;

import com.max.bookwishlist.dto.CreateUserRequest;
import com.max.bookwishlist.dto.UpdateUserRequest;
import com.max.bookwishlist.exception.UserNotFoundException;
import com.max.bookwishlist.model.User;
import com.max.bookwishlist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    public User createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + createUserRequest.getEmail());
        }
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        User saved  = userRepository.save(user);
        return saved;


    }

    public User updateUser( Long id, UpdateUserRequest updateUserRequest) {
        User user = getUserById(id);
        user.setEmail(updateUserRequest.getEmail());
        user.setUsername(updateUserRequest.getUsername());
        User saved = userRepository.save(user);
        return saved;
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
