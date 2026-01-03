package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Categorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDto {

    private Integer id;

    private String nom;

    private Integer idEntreprise;

    public static CategorieDto fromEntity(Categorie categorie) {
        if (categorie == null){
            return null;
        }
        return CategorieDto.builder()
                .id(categorie.getId())
                .nom(categorie.getNom())
                .idEntreprise(categorie.getIdEntreprise())
                .build();
    }

    public static Categorie toEntity(CategorieDto dto){
        if (dto == null){
            return null;
        }
        Categorie  categorie = new Categorie();
        categorie.setId(dto.getId());
        categorie.setNom(dto.getNom());
        categorie.setIdEntreprise(dto.getIdEntreprise());

        return categorie;
    }
}
