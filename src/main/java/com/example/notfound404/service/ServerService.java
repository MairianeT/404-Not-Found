package com.example.notfound404.service;

import com.example.notfound404.service.dto.ServerDto;

import java.util.List;

public interface ServerService {
    List<ServerDto> findAll();
    List<ServerDto> findPublic();
    ServerDto findById(Long id);
    ServerDto findByCode (int code);
    ServerDto save (ServerDto server);
    void deleteServerById (Long id);
}
