package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Client;
import com.gestionStocks.gestionStock.model.Dette;
import com.gestionStocks.gestionStock.model.Ventes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetteDto {

    private BigDecimal montantRestant;

    private BigDecimal recouvrement;

    private Instant date;

    private Integer client;

    private Integer ventes;

    private Integer idEntreprise;

    public static DetteDto fromEntity(Dette dette){
        if (dette == null){
            return null;
        }
        return DetteDto.builder()
                .client(dette.getClient().getId())
                .date(dette.getDate())
                .montantRestant(dette.getMontantRestant())
                .recouvrement(dette.getRecouvrement())
                .ventes(dette.getVentes().getId())
                .idEntreprise(dette.getIdEntreprise())
                .build();
    }
    public static Dette ToEntity(DetteDto dto){
        if (dto == null){
            return null;
        }
        Dette dette = new Dette();
        dette.setDate(dto.getDate());
        dette.setMontantRestant(dto.getMontantRestant());
        dette.setRecouvrement(dto.getRecouvrement());
        dette.setIdEntreprise(dto.getIdEntreprise());

        Client client = new Client();
        client.setId(dto.getClient());
        dette.setClient(client);

        Ventes ventes = new Ventes();
        ventes.setId(dto.getVentes());
        dette.setVentes(ventes);

        return dette;
    }
}
