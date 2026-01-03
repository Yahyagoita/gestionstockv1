package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Client;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private String nomClient;
    private String telClient;
    private Integer idEntreprise;
    public static ClientDto fromEntity(Client client){
        if (client == null){
            return null;
        }
        return ClientDto.builder()
                .nomClient(client.getNomClient())
                .telClient(client.getTelClient())
                .idEntreprise(client.getIdEntreprise())
                .build();
    }
    public static Client toEntity(ClientDto dto){
        if (dto == null){
            return null;
        }
        Client client = new Client();
        client.setNomClient(dto.getNomClient());
        client.setTelClient(dto.getTelClient());
        client.setIdEntreprise(dto.getIdEntreprise());

        return client;
    }
}
