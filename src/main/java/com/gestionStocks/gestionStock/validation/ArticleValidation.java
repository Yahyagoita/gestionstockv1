package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidation {

    public static List<String> validate(ArticleDto  dto){
          List<String> errors =  new ArrayList<>();
          if(dto == null){
              errors.add("Veuillez renseigner le code de l'article");
              errors.add("Veuillez renseigner le nom de l'article");
              errors.add("Veuillez renseigner la description de l'article");
              errors.add("Veuillez renseigner la quantite de l'article");
              errors.add("Veuillez renseigner le prix Unitaire de l'article");
              errors.add("Veuillez renseigner le Id de l'Entreprise de l'article");
              errors.add("Veuillez renseigner le Categorie de l'article");

              return errors;
          }
          if (!StringUtils.hasLength(dto.getCodeArticle())){
              errors.add("Veuillez renseigner le code de l'article");
          }
          if (!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez renseigner le nom de l'article");
          }
          /*
          if (!StringUtils.hasLength(dto.getDespcrition())){
            errors.add("Veuillez renseigner la description de l'article");
          }

           */
        if (dto.getQuantite() == null){
            errors.add("Veuillez renseigner la quantite de l'article");
        }
          if (dto.getPrixUnitaire() == null){
              errors.add("Veuillez renseigner le prix Unitaire de l'article");
          }
        if (dto.getIdEntreprise() == null){
            errors.add("Veuillez renseigner le Id de l'Entreprise de l'article");
        }
        if (dto.getCategorie() == null){
            errors.add("Veuillez renseigner la categorie de l'article");
        }


          return errors;
    }
}
