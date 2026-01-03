package com.gestionStocks.gestionStock.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Table(name = "mvtStck")
@EqualsAndHashCode(callSuper = true)
public class MvtStck extends AbstractEntity{

    @Column(name = "dateMvt")
    private Instant dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @Column(name = "typemvt")
    @Enumerated(EnumType.STRING)
    private TypeMvt typeMvt;

    @ManyToOne
    @JoinColumn(name = "idArticle")
    private Article article;
}
