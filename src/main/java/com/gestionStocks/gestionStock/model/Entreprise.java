package com.gestionStocks.gestionStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@Table(name = "entreprise")
@EqualsAndHashCode(callSuper = true)
public class Entreprise extends AbstractEntity{

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "motpassse")
    private String motPasse;

    @OneToMany(mappedBy = "idEntreprise", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Utilisateur> utilisateurs;

}
