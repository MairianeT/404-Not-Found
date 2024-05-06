package com.example.notfound404.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameServerDto {
    private UUID id;
    private UUID creatorId;;
    private UUID userId;
    private int code;
    private Boolean isPublic;
}
