package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Article;
import com.gestionStocks.gestionStock.model.LigneVentes;
import com.gestionStocks.gestionStock.model.Ventes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LigneVentesDto {

    private Integer id;

    private BigDecimal quantite;

    private BigDecimal prixUnitaireDefaut;

    private BigDecimal montant;

    private Integer article;

    private Integer ventes;

    private Integer idEntreprise;

    public static LigneVentesDto fromEntity(LigneVentes ligneVentes) {
        if (ligneVentes == null) {
            return null;
        }

        return LigneVentesDto.builder()
                .id(ligneVentes.getId())
                .ventes(ligneVentes.getArticle().getId())
                .article(ligneVentes.getVentes().getId())
                .quantite(ligneVentes.getQuantite())
                .prixUnitaireDefaut(ligneVentes.getPrixUnitaireDefaut())
                .idEntreprise(ligneVentes.getIdEntreprise())
                .montant(ligneVentes.getMontant())
                .build();
    }

    public static LigneVentes toEntity(LigneVentesDto dto) {
        if (dto == null) {
            return null;
        }
        LigneVentes ligneVentes = new LigneVentes();
        ligneVentes.setId(dto.getId());
        ligneVentes.setQuantite(dto.getQuantite());
        ligneVentes.setPrixUnitaireDefaut(dto.getPrixUnitaireDefaut());
        ligneVentes.setIdEntreprise(dto.getIdEntreprise());
        ligneVentes.setMontant(dto.getMontant());

        Ventes vente = new Ventes();
        vente.setId(dto.ventes);
        ligneVentes.setVentes(vente);

        Article article = new Article();
        article.setId(dto.article);
        ligneVentes.setArticle(article);

        return ligneVentes;
    }
}
