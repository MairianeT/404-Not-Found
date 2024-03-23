package com.example.notfound404.controller;

import com.example.notfound404.service.ServerService;
import com.example.notfound404.service.dto.ServerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController

@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @GetMapping("/servers")
    public List<ServerDto> allServers() {
        return serverService.findAll();
    }

    @PutMapping("/servers/connect/{code}")
    public ResponseEntity<ServerDto> getServerByCode(@PathVariable("code") int code, @RequestParam("userId") String userId) {
        ServerDto serverDto = serverService.findByCodeAndSetUserId(code, userId);
        if (serverDto != null) {
            return ResponseEntity.ok().body(serverDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/public-servers")
    public List<ServerDto> getPublicServers() {
        return serverService.findPublic();
    }

    @GetMapping("/public-servers/connect/{id}")
    public ResponseEntity<ServerDto> getServer(@PathVariable("id") Long id, @RequestParam("userId") String userId) {
        return ResponseEntity.ok().body(serverService.findById(id, userId));
    }

    @PostMapping("/servers")
    public ResponseEntity<ServerDto> createServer( @RequestBody ServerDto server) throws URISyntaxException {
        ServerDto result = serverService.save(server);
        return ResponseEntity.created(new URI("/api/servers/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable("id") Long id) {
        serverService.deleteServerById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/servers/byUserId/{userId}")
    public ResponseEntity<Void> deleteServersByUserId(@PathVariable("userId") String userId) {
        serverService.deleteAllServerByIdByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
