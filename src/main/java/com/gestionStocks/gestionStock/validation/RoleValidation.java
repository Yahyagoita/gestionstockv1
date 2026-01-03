package com.gestionStocks.gestionStock.validation;

import com.gestionStocks.gestionStock.dto.RoleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RoleValidation {

    public static List<String> validate(RoleDto dto){
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("Veuillez rensegner le nom du Role");
            errors.add("Veuillez rensegner l'id de l'entreprise du Role");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getNomrole())){
            errors.add("Veuillez rensegner le nom du Role");
        }
        if (dto.getIdEntrepise()== null){
            errors.add("Veuillez rensegner l'id de l'entreprise du Role");
        }
            return errors;
    }
}
