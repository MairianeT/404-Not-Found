package com.example.notfound404.models;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_server")
public class GameServer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "creator_id")
    private UUID creatorId;

    @Column(name = "user_id", nullable = true)
    private UUID userId;

    @Column(name = "code")
    private int code;

    @Column(name = "is_public")
    private Boolean isPublic;
}
