package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.DetteDto;

import java.util.ArrayList;
import java.util.List;

public class DetteValidation {

    public static List<String> validate(DetteDto dto){
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("Veuillez rensegne le montant du recouvrement");
            errors.add("Veuillez rensegne l'id du client");
            errors.add("Veuillez rensegne le l'id de l'entreprise");
            return errors;
        }
        if (dto.getIdEntreprise() == null){
            errors.add("Veuillez rensegne le l'id de l'entreprise");
        }
        if (dto.getClient() == null){
            errors.add("Veuillez rensegne le l'id du client");
        }
        if (dto.getMontantRecouvrement() == null){
            errors.add("Veuillez rensegne le montant du recouvrement");
        }
       return errors;
    }
}
