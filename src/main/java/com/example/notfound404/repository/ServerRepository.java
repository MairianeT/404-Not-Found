package com.example.notfound404.repository;

import com.example.notfound404.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    List<Server> findByIsPublicTrue();
    Optional<Server> findByCode(int code);
}
