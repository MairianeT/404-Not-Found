package com.example.notfound404.service;

import com.example.notfound404.models.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
    User create();
    Boolean isCookieValid(UUID id, String cookie);
}
