package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Entreprise;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDto {

    private Integer id;

    private String nom;

    private String email;

    private String tel;

    private String motPasse;

    public static EntrepriseDto fromEntity(Entreprise entreprise){
        if (entreprise == null){
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .email(entreprise.getEmail())
                .tel(entreprise.getTel())
                .motPasse(entreprise.getMotPasse())
                .build();
    }
    public static Entreprise toEntity(EntrepriseDto dto){
        if (dto == null){
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(dto.getId());
        entreprise.setNom(dto.getNom());
        entreprise.setEmail(dto.getEmail());
        entreprise.setTel(dto.getTel());
        entreprise.setMotPasse(dto.getMotPasse());

        return entreprise;
    }
}
