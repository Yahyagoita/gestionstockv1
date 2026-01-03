package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Article;
import com.gestionStocks.gestionStock.model.Categorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Integer id;

    private String codeArticle;

    private String nom;

    private String despcrition;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    private Integer categorie;

    public static ArticleDto fromEntity(Article article){
        if (article == null){
            return null;
        }
        return ArticleDto.builder()
                .id(article.getId())
                .categorie(article.getIdCategorie().getId())
                .nom(article.getNom())
                .despcrition(article.getDespcrition())
                .quantite(article.getQuantite())
                .prixUnitaire(article.getPrixUnitaire())
                .idEntreprise(article.getIdEntreprise())
                .build();
    }
     public static  Article toEntity(ArticleDto dto){
        if (dto == null){
            return null;
        }
        Article article = new Article();
        article.setId(dto.getId());
        article.setNom(dto.getNom());
        article.setCodeArticle(dto.getCodeArticle());
        article.setDespcrition(dto.getDespcrition());
        article.setQuantite(dto.getQuantite());
        article.setIdEntreprise(dto.getIdEntreprise());
        article.setPrixUnitaire(dto.getPrixUnitaire());

        Categorie cat = new Categorie();
        cat.setId(dto.getCategorie());
        article.setIdCategorie(cat);

        return article;
     }
}
