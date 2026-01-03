package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Role;
import com.gestionStocks.gestionStock.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Integer id;

    private String nomrole;

    private Integer idEntrepise;

    public static RoleDto fromEntity(Role role){
        if (role == null){
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                .nomrole(role.getNomRole())
                .idEntrepise(role.getIdEntreprise())
                .build();
    }
    public static Role toEntity(RoleDto dto){
        if (dto == null){
            return null;
        }
        Role role = new Role();
        role.setId(dto.getId());
        role.setNomRole(dto.getNomrole());
        role.setIdEntreprise(dto.getIdEntrepise());

        return role;
    }
}
