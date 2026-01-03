package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto dto);
    ClientDto update(Integer id, ClientDto dto);
    List<ClientDto>  findByEntre(Integer id);
    void delete(Integer id);
}
