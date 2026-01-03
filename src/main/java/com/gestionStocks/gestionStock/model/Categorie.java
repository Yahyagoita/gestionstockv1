package com.gestionStocks.gestionStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@Table(name = "categorie")
@EqualsAndHashCode(callSuper = true)
public class Categorie extends AbstractEntity{

    @Column(name = "nom")
    private String nom;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "idCategorie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Article> articles;
}
