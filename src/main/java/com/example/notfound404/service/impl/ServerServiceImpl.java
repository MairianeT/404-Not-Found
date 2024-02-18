package com.example.notfound404.service.impl;

import com.example.notfound404.models.Server;
import com.example.notfound404.repository.ServerRepository;
import com.example.notfound404.service.ServerService;
import com.example.notfound404.service.convertor.ServerMapper;
import com.example.notfound404.service.dto.ServerDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    public ServerDto findById(Long id) {
        return Optional.of(getById(id)).map(serverMapper::modelToDto).get();
    }

    @Override
    public ServerDto findByCode(int code) {
        return serverRepository.findByCode(code)
                .map(serverMapper::modelToDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public ServerDto save(ServerDto server) {
        int uniqueCode = generateUniqueCode();
        server.setCode(uniqueCode);
        return serverMapper.modelToDto(serverRepository.save(
                serverMapper.dtoToModel(server)
        ));
    }

    @Override
    @Transactional
    public void deleteServerById(Long id) {
        var server = getById(id);
        serverRepository.delete(server);
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
