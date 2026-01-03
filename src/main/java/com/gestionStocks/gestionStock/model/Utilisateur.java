package com.gestionStocks.gestionStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@Table(name = "utilisateurs")
@EqualsAndHashCode(callSuper = true)
public class Utilisateur extends  AbstractEntity{

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "motpasse")
    private String motPasse;

    @Column(name = "tel")
    private String tel;

    @ManyToOne
    @JoinColumn(name = "idEntreprise")
    private Entreprise idEntreprise;

    @ManyToOne
    @JoinColumn(name = "idRole")
    private Role role;

}
