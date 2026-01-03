package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.CategorieDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategorieValidation {

    public static List<String> validate(CategorieDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez rensegne le nom de la categorie");
            errors.add("Veuillez rensegne le Id de l'entreprise de la categorie");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez rensegne le nom de la categorie");
        }
        if (dto.getIdEntreprise() == null){
            errors.add("Veuillez rensegne le Id de l'entreprise de la categorie");
        }
        return errors;
    }
}
