package com.example.notfound404.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "server")
public class Server {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "user_id", nullable = true)
    private String userId;

    @Column(name = "code")
    private int code;

    @Column(name = "is_public")
    private Boolean isPublic;
}
