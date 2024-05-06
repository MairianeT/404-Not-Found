package com.example.notfound404.repository;

import com.example.notfound404.models.GameServer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameServerRepository extends JpaRepository<GameServer, UUID> {
    List<GameServer> findByIsPublicTrue();
    Optional<GameServer> findByCode(int code);
    List<GameServer> findByCreatorIdOrUserId(UUID creatorId, UUID userId);
}
