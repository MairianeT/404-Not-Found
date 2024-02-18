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

    @GetMapping("/servers/{code}")
    public ResponseEntity<ServerDto> getServerByCode(@PathVariable int code) {
        ServerDto serverDto = serverService.findByCode(code);
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

    @GetMapping("/servers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ServerDto> getServer(@PathVariable Long id) {
        return ResponseEntity.ok().body(serverService.findById(id));
    }

    @PostMapping("/server")
    public ResponseEntity<ServerDto> createBook( @RequestBody ServerDto book) throws URISyntaxException {
        ServerDto result = serverService.save(book);
        return ResponseEntity.created(new URI("/api/servers/" + result.getId()))
                .body(result);
    }

    @PutMapping("/server/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ServerDto> updateBook( @PathVariable Long id, @RequestBody ServerDto server) {
        return ResponseEntity.ok().body(serverService.save(server));
    }

    @DeleteMapping("/server/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        serverService.deleteServerById(id);
        return ResponseEntity.ok().build();
    }
}
