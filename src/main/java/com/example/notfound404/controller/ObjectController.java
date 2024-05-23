package com.example.notfound404.controller;

import com.example.notfound404.service.ObjectService;
import com.example.notfound404.service.dto.ObjectDto;
import com.example.notfound404.service.dto.ObjectCreateDto;
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
public class ObjectController {
    private final ObjectService objectService;
    private final UserController userController;

    @PostMapping("/objects")
    public ResponseEntity<ObjectDto> createObject(@RequestBody ObjectCreateDto object, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        ObjectDto result = objectService.create(object);
        return ResponseEntity.created(new URI("/api/objects/" + result.getId())).body(result);
    }

    @GetMapping("object/{id}")
    public ResponseEntity<ObjectDto> getObjectById(@PathVariable("id") UUID id, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        ObjectDto object = objectService.getById(id);
        if (object != null) {
            return ResponseEntity.ok().body(object);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("object/{server_id}")
    public ResponseEntity<List<ObjectDto>> getObjectByServerId(@PathVariable("server_id") UUID serverId, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException  {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();
        List<ObjectDto> objectList = objectService.getByServerId(serverId);
        if (objectList != null) {
            return ResponseEntity.ok().body(objectList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("object/{name}")
    public ResponseEntity<ObjectDto> getObjectByName(@PathVariable("name") String name, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException  {
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        ObjectDto object = objectService.getByName(name);
        if (object != null) {
            return ResponseEntity.ok().body(object);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("object/{id}/{value}")
    public ResponseEntity<Void> setObjectValue(@PathVariable("id") UUID id, @PathVariable("value") int value, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException{
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        objectService.setValue(id, value);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("object/{id}/{text_value}")
    public ResponseEntity<Void> setObjectValue(@PathVariable("id") UUID id, @PathVariable("text_value") String textValue, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException{
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        objectService.setTextValue(id, textValue);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("object/{id}")
    public ResponseEntity<Void> setObjectChanged(@PathVariable("id") UUID id, @RequestParam(name = "userId") UUID userId, HttpServletRequest request) throws URISyntaxException{
        if (userController.isValidatedUser(userId, request))
            return ResponseEntity.badRequest().build();

        objectService.setChanged(id);
        return ResponseEntity.noContent().build();
    }
}