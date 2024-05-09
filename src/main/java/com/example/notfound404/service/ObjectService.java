package com.example.notfound404.service;

import com.example.notfound404.models.Object;
import com.example.notfound404.service.dto.ObjectCreateDto;
import com.example.notfound404.service.dto.ObjectDto;

import java.util.List;
import java.util.UUID;

public interface ObjectService {
    ObjectDto create (ObjectCreateDto object);
    ObjectDto getById (UUID id);
    List<ObjectDto> getByServerId (UUID id);
    ObjectDto getByName (String name);
    void setValue (UUID id, int value);
    void setTextValue (UUID id, String textValue);
    void setChanged (UUID id);

}
