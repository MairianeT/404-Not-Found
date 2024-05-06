package com.example.notfound404.service;

import com.example.notfound404.service.dto.GameServerDto;

import java.util.List;
import java.util.UUID;

public interface GameServerService {
    List<GameServerDto> findAll();
    List<GameServerDto> findPublic();
    GameServerDto findByIdAndSetUserId(UUID id, UUID userId);
    GameServerDto findByCodeAndSetUserId (int code, UUID userId);
    GameServerDto create (GameServerDto server);
    void deleteServerById (UUID id);
    void deleteAllServerByIdByUserId (UUID userId);
}
