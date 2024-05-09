package com.example.notfound404.service.impl;

import com.example.notfound404.exception.ServerNotFoundException;
import com.example.notfound404.models.Object;
import com.example.notfound404.repository.ObjectRepository;
import com.example.notfound404.service.ObjectService;
import com.example.notfound404.service.convertor.ObjectMapper;
import com.example.notfound404.service.dto.ObjectCreateDto;
import com.example.notfound404.service.dto.ObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ObjectServiceImlp implements ObjectService {
    private final ObjectRepository objectRepository;
    private final ObjectMapper objectMapper;
    @Override
    @Transactional
    public ObjectDto create(ObjectCreateDto object) {
        return objectMapper.modelToDto(objectRepository.save(
                objectMapper.dtoCreateToModel(object)
        ));
    }

    @Override
    public ObjectDto getById(UUID id) {
        return objectMapper.modelToDto(getObjectById(id));
    }

    @Override
    public List<ObjectDto> getByServerId(UUID serverId) {
        return objectMapper.toListDto(objectRepository.findByServerId(serverId));
    }

    @Override
    public ObjectDto getByName(String name) {
        return objectMapper.modelToDto(objectRepository.findByName(name));
    }

    @Override
    @Transactional
    public void setValue(UUID id, int value) {
        Object object = getObjectById(id);
        object.setValue(value);
    }

    @Override
    @Transactional
    public void setTextValue(UUID id, String textValue) {
        Object object = getObjectById(id);
        object.setTextValue(textValue);
    }

    @Override
    @Transactional
    public void setChanged(UUID id) {
        Object object = getObjectById(id);
        object.setIsChanged(!object.getIsChanged());
    }
    private Object getObjectById(UUID id)
    {
        return objectRepository.findById(id).orElseThrow(() -> new ServerNotFoundException("Object with id " + id + " not found."));
    }
}
