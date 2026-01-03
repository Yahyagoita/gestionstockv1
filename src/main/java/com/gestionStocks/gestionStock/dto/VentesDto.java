package com.gestionStocks.gestionStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestionStocks.gestionStock.model.Ventes;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentesDto {
    private Integer id;

    private Instant dateVente;

    private String commentaire;

    private Integer idEntreprise;

    private BigDecimal montantPaye;

    private String nomClient;

    private String telClient;

    private List<LigneVentesDto> ligneVentes =new ArrayList<>();


    public static VentesDto fromEntity(Ventes ventes){
        if (ventes == null){
            return null;
        }
        return VentesDto.builder()
                .id(ventes.getId())
                .idEntreprise(ventes.getIdEntreprise())
                .commentaire(ventes.getCommentaire())
                .montantPaye(ventes.getMontantPaye())
                .dateVente(ventes.getDateVente())
                .build();
    }
    public static Ventes toEntity(VentesDto dto){
        if (dto == null){
            return null;
        }
        Ventes ventes = new Ventes();
        ventes.setId(dto.getId());
        ventes.setDateVente(dto.getDateVente());
        ventes.setCommentaire(dto.getCommentaire());
        ventes.setIdEntreprise(dto.getIdEntreprise());
        ventes.setMontantPaye(dto.getMontantPaye());
        return ventes;
    }
}
