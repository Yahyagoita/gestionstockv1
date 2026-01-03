package com.gestionStocks.gestionStock.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "ligneventes")
public class LigneVentes extends AbstractEntity{

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixUnitaireDefaut")
    private BigDecimal prixUnitaireDefaut;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @ManyToOne
    @JoinColumn(name = "idArticle")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "idVentes")
    private Ventes ventes;

}
