package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidation {

    public static List<String> validate(EntrepriseDto dto){
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("Veuillez rensegner le nom de l'Entreprise");
            errors.add("Veuillez rensegner le mail de l'Entreprise");
            errors.add("Veuillez rensegner le mot de passe de l'Entreprise");
            errors.add("Veuillez rensegner le numero de telephone de l'Entreprise");

            return errors;
        }
        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez rensegner le nom de l'Entreprise");
        }
        if (!StringUtils.hasLength(dto.getEmail())){
            errors.add("Veuillez rensegner le mail de l'Entreprise");
        }
        if (!StringUtils.hasLength(dto.getMotPasse())){
            errors.add("Veuillez rensegner le mot de passe de l'Entreprise");
        }
        if (!StringUtils.hasLength(dto.getTel())){
            errors.add("Veuillez rensegner le numero de telephone de l'Entreprise");
        }
        return errors;
    }
}
