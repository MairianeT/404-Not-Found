package com.example.notfound404.repository;

import com.example.notfound404.models.GameServer;
import com.example.notfound404.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface  UserRepository extends JpaRepository<User, UUID> {
}
