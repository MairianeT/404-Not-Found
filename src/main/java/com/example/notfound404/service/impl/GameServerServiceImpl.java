package com.example.notfound404.service.impl;

import com.example.notfound404.exception.InvalidServerException;
import com.example.notfound404.exception.ServerNotFoundException;
import com.example.notfound404.models.GameServer;
import com.example.notfound404.repository.GameServerRepository;
import com.example.notfound404.service.GameServerService;
import com.example.notfound404.service.convertor.GameServerMapper;
import com.example.notfound404.service.dto.GameServerDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GameServerServiceImpl implements GameServerService {
    private final GameServerRepository gameServerRepository;
    private final GameServerMapper gameServerMapper;
    @Override
    public List<GameServerDto> findAll() {
        return gameServerMapper.toListDto(gameServerRepository.findAll());
    }

    @Override
    public List<GameServerDto> findPublic() {
        return gameServerMapper.toListDto(gameServerRepository.findByIsPublicTrue());
    }

    @Override
    @Transactional
    public GameServerDto findByIdAndSetUserId(UUID id, UUID userId) {
        return gameServerRepository.findById(id)
                .map(server -> {
                    if (server.getIsPublic()) {
                        server.setUserId(userId);
                        server.setIsPublic(false);
                        gameServerRepository.save(server);
                        return gameServerMapper.modelToDto(server);
                    } else {
                        throw new InvalidServerException("The server with id " + id + " is not public.");
                    }
                })
                .orElseThrow(() -> new ServerNotFoundException("Server with id " + id + " not found."));
    }

    @Override
    @Transactional
    public GameServerDto findByCodeAndSetUserId(int code, UUID userId) {
        List<GameServer> serversToDelete = gameServerRepository.findByCreatorIdOrUserId(userId, userId);
        if (!serversToDelete.isEmpty()) {
            gameServerRepository.deleteAll(serversToDelete);
        }

        return gameServerRepository.findByCode(code)
                .map(server -> {
                    server.setUserId(userId);
                    gameServerRepository.save(server);
                    return gameServerMapper.modelToDto(server);
                })
                .orElseThrow(() -> new ServerNotFoundException("Server with code " + code + " not found."));
    }

    @Override
    @Transactional
    public GameServerDto create(GameServerDto server) {
        List<GameServer> serversToDelete = gameServerRepository.findByCreatorIdOrUserId(server.getCreatorId(), server.getCreatorId());
        if (!serversToDelete.isEmpty()) {
            gameServerRepository.deleteAll(serversToDelete);
        }

        int uniqueCode = generateUniqueCode();
        server.setCode(uniqueCode);
        return gameServerMapper.modelToDto(gameServerRepository.save(
                gameServerMapper.dtoToModel(server)
        ));
    }

    @Override
    @Transactional
    public void deleteServerById(UUID id) {
        try {
            gameServerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ServerNotFoundException("Server with id " + id + " not found.");
        }
    }

    @Override
    @Transactional
    public void deleteAllServerByIdByUserId(UUID userId) {
        List<GameServer> serversToDelete = gameServerRepository.findByCreatorIdOrUserId(userId, userId);
        if (!serversToDelete.isEmpty()) {
            gameServerRepository.deleteAll(serversToDelete);
        } else {
            throw new ServerNotFoundException("No servers found for user with id " + userId);
        }
    }

    private GameServer getById(UUID id) {
        return gameServerRepository.findById(id)
                .orElseThrow(() -> new ServerNotFoundException("Server with id " + id + " not found."));
    }

    private int generateUniqueCode() {
        UUID uuid = UUID.randomUUID();
        long longValue = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        return Math.abs((int) longValue);
    }
}
