package com.example.notfound404.controller;

import com.example.notfound404.models.User;
import com.example.notfound404.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser() throws URISyntaxException {
        User user = userService.create();
        return ResponseEntity.created(new URI("/api/users/" + user.getId()))
                .body(user);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("users/{id}/validate")
    public ResponseEntity<String> validateUser(
            @PathVariable("id") UUID id,
            HttpServletRequest request) {

        String cookieValue = extractCookieValue(request);

        User user = userService.getUserById(id);

        if (user != null) {
            if (userService.isCookieValid(id, cookieValue)) {
                return ResponseEntity.ok("User validated successfully.");
            } else {
                return ResponseEntity.badRequest().body("Invalid cookie for the user.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String extractCookieValue(HttpServletRequest request) {
        String cookieValue = null;
        if (request != null && request.getHeader("Cookie") != null) {
            String[] cookies = request.getHeader("Cookie").split("; ");
            for (String cookie : cookies) {
                if (cookie.startsWith("cookie=")) {
                    cookieValue = cookie.substring("cookie=".length());
                    break;
                }
            }
        }
        return cookieValue;
    }
}
