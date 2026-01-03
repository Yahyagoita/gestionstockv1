package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VentesValidation {

    public static List<String> validate(VentesDto dto) {
        List<String> errors = new ArrayList<>();
       /* if (dto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            return errors;
        }
        */

        if (dto.getIdEntreprise() == null) {
            errors.add("Veuillez renseigner l'Id de l'Entreprise");
        }
        if (dto.getDateVente() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }

        return errors;
    }
}
