package com.example.notfound404.service.impl;

import com.example.notfound404.models.Server;
import com.example.notfound404.repository.ServerRepository;
import com.example.notfound404.service.ServerService;
import com.example.notfound404.service.convertor.ServerMapper;
import com.example.notfound404.service.dto.ServerDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;
    private final ServerMapper serverMapper;
    @Override
    public List<ServerDto> findAll() {
        return serverMapper.toListDto(serverRepository.findAll());
    }

    @Override
    public List<ServerDto> findPublic() {
        return serverMapper.toListDto(serverRepository.findByIsPublicTrue());
    }

    @Override
    @Transactional
    public ServerDto findById(Long id, String userId) {
        return serverRepository.findById(id)
                .map(server -> {
                    if (server.getIsPublic()) {
                        server.setUserId(userId);
                        server.setIsPublic(false);
                        serverRepository.save(server);
                        return serverMapper.modelToDto(server);
                    } else {
                        return null;
                    }
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public ServerDto findByCodeAndSetUserId(int code, String userId) {
        List<Server> serversToDelete = serverRepository.findByCreatorIdOrUserId(userId, userId);
        if (!serversToDelete.isEmpty()) {
            serverRepository.deleteAll(serversToDelete);
        }

        return serverRepository.findByCode(code)
                .map(server -> {
                    server.setUserId(userId);
                    serverRepository.save(server);
                    return serverMapper.modelToDto(server);
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public ServerDto save(ServerDto server) {
        List<Server> serversToDelete = serverRepository.findByCreatorIdOrUserId(server.getCreatorId(), server.getCreatorId());
        if (!serversToDelete.isEmpty()) {
            serverRepository.deleteAll(serversToDelete);
        }

        int uniqueCode = generateUniqueCode();
        server.setCode(uniqueCode);
        return serverMapper.modelToDto(serverRepository.save(
                serverMapper.dtoToModel(server)
        ));
    }

    @Override
    @Transactional
    public void deleteServerById(Long id) {
        serverRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllServerByIdByUserId(String userId) {
        List<Server> serversToDelete = serverRepository.findByCreatorIdOrUserId(userId, userId);
        if (!serversToDelete.isEmpty()) {
            serverRepository.deleteAll(serversToDelete);
        }
    }

    private Server getById(Long id) {
        return serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Book with id: " + id + " not found"));
    }

    private int generateUniqueCode() {
        UUID uuid = UUID.randomUUID();
        long longValue = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        return Math.abs((int) longValue);
    }
}
