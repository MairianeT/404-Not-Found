package com.example.notfound404.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerDto {
    private Long id;
    private String creatorId;;
    private String userId;
    private int code;
    private Boolean isPublic;
}
