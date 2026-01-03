package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidation {

    public static List<String> validate(UtilisateurDto dto){
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("Veuillez rensegner le nom de l'Utilisateur");
            errors.add("Veuillez rensegner le prenom de l'Utilisateur");
            errors.add("Veuillez rensegner le mail de l'Utilisateur");
            errors.add("Veuillez rensegner le mot de passe de l'Utilisateur");
            errors.add("Veuillez rensegner le numero de telephone de l'Utilisateur");
            errors.add("Veuillez rensegner le role de l'Utilisateur");

            return errors;
        }
        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez rensegner le nom de l'Utilisateur");
        }
        if (!StringUtils.hasLength(dto.getPrenom())){
            errors.add("Veuillez rensegner le prenom de l'Utilisateur");
        }
        if (!StringUtils.hasLength(dto.getEmail())){
            errors.add("Veuillez rensegner le mail de l'Utilisateur");
        }
        if (!StringUtils.hasLength(dto.getMotPasse())){
            errors.add("Veuillez rensegner le mot de passe de l'Utilisateur");
        }
        if (!StringUtils.hasLength(dto.getTel())){
            errors.add("Veuillez rensegner le numero de telephone de l'Utilisateur");
        }
        if (dto.getRole() == null){
            errors.add("Veuillez rensegner le role de l'Utilisateur");
        }
        return errors;
    }
}
