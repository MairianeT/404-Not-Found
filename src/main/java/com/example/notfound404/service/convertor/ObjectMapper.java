package com.example.notfound404.service.convertor;

import com.example.notfound404.models.Object;
import com.example.notfound404.service.dto.ObjectCreateDto;
import com.example.notfound404.service.dto.ObjectDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ObjectMapper {
    Object dtoToModel(ObjectDto objectDto);
    Object dtoCreateToModel(ObjectCreateDto objectDto);
    ObjectDto modelToDto(Object object);
    ObjectCreateDto modelToCreateDto(Object object);
    List<ObjectDto> toListDto(List<Object> Server);
}
