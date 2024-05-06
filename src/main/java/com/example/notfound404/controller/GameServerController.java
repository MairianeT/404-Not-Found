package com.example.notfound404.controller;

import com.example.notfound404.service.GameServerService;
import com.example.notfound404.service.dto.GameServerDto;
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

    @GetMapping("/servers")
    public List<GameServerDto> allServers() {
        return gameServerService.findAll();
    }

    @PutMapping("/servers/connect/{code}")
    public ResponseEntity<GameServerDto> getServerByCode(@PathVariable("code") int code, @RequestParam("userId") UUID userId) {
        GameServerDto gameServerDto = gameServerService.findByCodeAndSetUserId(code, userId);
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
    public ResponseEntity<GameServerDto> getServer(@PathVariable("id") UUID id, @RequestParam("userId") UUID userId) {
        return ResponseEntity.ok().body(gameServerService.findByIdAndSetUserId(id, userId));
    }

    @PostMapping("/servers")
    public ResponseEntity<GameServerDto> createServer(@RequestBody GameServerDto server) throws URISyntaxException {
        GameServerDto result = gameServerService.create(server);
        return ResponseEntity.created(new URI("/api/servers/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable("id") UUID id) {
        gameServerService.deleteServerById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/servers/byUserId/{userId}")
    public ResponseEntity<Void> deleteServersByUserId(@PathVariable("userId") UUID userId) {
        gameServerService.deleteAllServerByIdByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
