package com.example.notfound404.service.impl;

import com.example.notfound404.exception.UserNotFoundException;
import com.example.notfound404.models.User;
import com.example.notfound404.repository.UserRepository;
import com.example.notfound404.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }

    @Override
    public User create() {
        String cookie = generateCookie();
        User user = new User();
        user.setCookie(cookie);
        return userRepository.save(user);
    }

    @Override
    public Boolean isCookieValid(UUID id, String cookie) {
        User user = getUserById(id);
        return (user.getCookie() != null && user.getCookie().equals(cookie));
    }

    private String generateCookie() {
        try {
            String randomValue = UUID.randomUUID().toString();
            String timestamp = String.valueOf(System.currentTimeMillis());

            String combinedValue = randomValue + timestamp;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combinedValue.getBytes());

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
