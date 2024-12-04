package com.example.userserver.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Transactional
    public UserInfo createUser(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()) != null) {
            throw new IllegalArgumentException("Username duplicated");
        }

        String hashedPassword = passwordEncoder.encode(userRequest.getPlainPassword());
        User user = new User(userRequest.getUsername(), userRequest.getEmail(), hashedPassword);
        User savedUser = userRepository.save(user);

        return new UserInfo(savedUser);
    }

    public UserInfo getUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        return new UserInfo(user);
    }

    public UserInfo getUserByName(String name) {
        User user = getByUsername(name);
        return new UserInfo(user);
    }

    public UserInfo signIn(UserRequest signInRequest) {
        User user = getByUsername(signInRequest.getUsername());

        boolean isPasswordMatch = passwordEncoder.matches(signInRequest.getPlainPassword(), user.getPassword());
        if (isPasswordMatch) {
            throw new IllegalArgumentException("Failed to sign in");
        }

        return new UserInfo(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }
}
