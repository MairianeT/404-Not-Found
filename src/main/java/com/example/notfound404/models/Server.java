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

    @Column(name = "creatorId")
    private String creatorId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "code")
    private int code;

    @Column(name = "isPublic")
    private Boolean isPublic;
}
