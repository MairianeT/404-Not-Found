package com.example.notfound404.service.convertor;

import com.example.notfound404.models.Server;
import com.example.notfound404.service.dto.ServerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServerMapper {
        Server dtoToModel(ServerDto serverDto);

        ServerDto modelToDto(Server server);

        List<ServerDto> toListDto(List<Server> Server);
}
