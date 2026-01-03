package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Article;
import com.gestionStocks.gestionStock.model.MvtStck;
import com.gestionStocks.gestionStock.model.TypeMvt;
import jakarta.persistence.Column;
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
public class MvtStckDto {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private Integer idEntreprise;

    private TypeMvt typeMvt;

    private Integer article;

    public static MvtStckDto fromEntity(MvtStck mvtStck){
        if (mvtStck == null){
            return null;
        }
        return MvtStckDto.builder()
                .id(mvtStck.getId())
                .dateMvt(mvtStck.getDateMvt())
                .quantite(mvtStck.getQuantite())
                .idEntreprise(mvtStck.getIdEntreprise())
                .typeMvt(mvtStck.getTypeMvt())
                .article(mvtStck.getArticle().getId())
                .build();
    }
    public static MvtStck toEntity(MvtStckDto dto){
        if (dto == null){
            return null;
        }
        MvtStck mvtStck = new MvtStck();
        mvtStck.setId(dto.getId());
        mvtStck.setIdEntreprise(dto.getIdEntreprise());
        mvtStck.setDateMvt(dto.getDateMvt());
        mvtStck.setQuantite(dto.getQuantite());
        mvtStck.setTypeMvt(dto.getTypeMvt());

        Article article = new Article();
        article.setId(dto.getArticle());
        mvtStck.setArticle(article);
        return mvtStck;
    }
}
