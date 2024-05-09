package com.example.notfound404.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectDto {
    private UUID id;
    private String name;
    private int value;
    private String textValue;
    private Boolean isChanged;
}