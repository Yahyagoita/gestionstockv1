package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.LigneVentesDto;

import java.util.ArrayList;
import java.util.List;

public class LigneVentesValidation {

    public static List<String> validate(LigneVentesDto dto){
        List<String> errors = new ArrayList<>();

        if(dto.getQuantite() == null){
            errors.add("Veuillez rensegner la quantité de le l'article ");
        }
        if(dto.getIdEntreprise() == null){
            errors.add("Veuillez rensegner l'id de l'Entreprise");
        }
        return errors;

    }
}
