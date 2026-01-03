package com.gestionStocks.gestionStock.dto;

import com.gestionStocks.gestionStock.model.Entreprise;
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
public class UtilisateurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private String email;

    private String motPasse;

    private String tel;

    private Integer role;

    private Integer idEntreprise;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .motPasse(utilisateur.getMotPasse())
                .tel(utilisateur.getTel())
                .role(utilisateur.getRole().getId())
                .idEntreprise(utilisateur.getIdEntreprise().getId())
                .build();
    }
    public static Utilisateur toEntity(UtilisateurDto dto){
        if (dto == null){
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(dto.getId());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTel(dto.getTel());
        utilisateur.setMotPasse(dto.getMotPasse());

        Role role = new Role();
        role.setId(dto.getRole());
        utilisateur.setRole(role);

        Entreprise entreprise = new Entreprise();
        entreprise.setId(dto.getIdEntreprise());
        utilisateur.setIdEntreprise(entreprise);

        return utilisateur;
    }
}
