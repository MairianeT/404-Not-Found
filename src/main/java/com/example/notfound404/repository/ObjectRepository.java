package com.example.notfound404.repository;

import com.example.notfound404.models.Object;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ObjectRepository extends JpaRepository<Object, UUID> {
    List<Object> findByServerId(UUID serverId);
    Object findByName(String name);
}
