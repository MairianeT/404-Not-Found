package com.example.notfound404.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "object")
public class Object {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "server_id")
    private UUID serverId;

     @Column(name = "name")
    private String name;

    @Column(name = "value", nullable = true)
    private int value;

    @Column(name = "text_value", nullable = true)
    private String textValue;

    @Column(name = "is_changed", nullable = true)
    private Boolean isChanged;
}
