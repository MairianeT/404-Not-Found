package com.example.notfound404.service.convertor;

import com.example.notfound404.models.GameServer;
import com.example.notfound404.service.dto.GameServerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameServerMapper {
        GameServer dtoToModel(GameServerDto gameServerDto);

        GameServerDto modelToDto(GameServer server);

        List<GameServerDto> toListDto(List<GameServer> Server);
}
