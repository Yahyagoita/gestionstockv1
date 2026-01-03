package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.ClientDto;
import com.gestionStocks.gestionStock.dto.DetteDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.model.Client;
import com.gestionStocks.gestionStock.model.Ventes;
import com.gestionStocks.gestionStock.repository.ClientRepository;
import com.gestionStocks.gestionStock.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    @Override
    public ClientDto save(ClientDto dto) {
        return ClientDto.fromEntity(
                clientRepository.save(ClientDto.toEntity(dto))
        );
    }

    @Override
    public ClientDto update(Integer id, ClientDto dto) {
        if (id == null){
            log.error("id is null");
        }
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("client introuvable dans la BDD"));
        client.setTelClient(dto.getTelClient());
        client.setNomClient(dto.getNomClient());
        return ClientDto.fromEntity(
                clientRepository.save(client)
        );
    }

    @Override
    public List<ClientDto> findByEntre(Integer id) {
        return clientRepository.findByIdEntreprise(id)
                .stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("id is null");
        }
        clientRepository.deleteById(id);

    }
}
