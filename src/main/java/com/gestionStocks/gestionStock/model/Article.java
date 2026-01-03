package com.gestionStocks.gestionStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "article")
@EqualsAndHashCode(callSuper = true)
public class Article extends AbstractEntity{

    @Column(name = "codeArticle")
    private String codeArticle;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String despcrition;

    @Column(name = "prixUnitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie idCategorie;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MvtStck> mvtStcks;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LigneVentes> ligneVentes;
}
