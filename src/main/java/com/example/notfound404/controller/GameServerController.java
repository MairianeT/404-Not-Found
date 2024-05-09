package com.example.notfound404.controller;

import com.example.notfound404.service.GameServerService;
import com.example.notfound404.service.dto.GameServerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("api")
@RequiredArgsConstructor
public class GameServerController {
    private final GameServerService gameServerService;
    private final UserController userController;

    @GetMapping("/servers")
    public List<GameServerDto> allServers() {
        return gameServerService.findAll();
    }

    @PutMapping("/servers/connect/{code}")
    public ResponseEntity<GameServerDto> getServerByCode(@PathVariable("code") int code, @RequestParam("userId") UUID userId, HttpServletRequest request) {
        GameServerDto gameServerDto = gameServerService.findByCodeAndSetUserId(code, userId);
        if (userController.isValidatedUser(userId, request))
                return ResponseEntity.badRequest().build();

        if (gameServerDto != null) {
            return ResponseEntity.ok().body(gameServerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/public-servers")
    public List<GameServerDto> getPublicServers() {
        return gameServerService.findPublic();
    }

    @GetMapping("/public-servers/connect/{id}")
    public ResponseEntity<GameServerDto> getServer(@PathVariable("id") UUID id, @RequestParam("userId") UUID userId, HttpServletRequest request) {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(gameServerService.findByIdAndSetUserId(id, userId));
    }

    @PostMapping("/servers")
    public ResponseEntity<GameServerDto> createServer(@RequestBody GameServerDto server, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        GameServerDto result = gameServerService.create(server);
        return ResponseEntity.created(new URI("/api/servers/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable("id") UUID id, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        gameServerService.deleteServerById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/servers/byUserId/{userId}")
    public ResponseEntity<Void> deleteServersByUserId(@PathVariable("userId") UUID userId, HttpServletRequest request) {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        gameServerService.deleteAllServerByIdByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
