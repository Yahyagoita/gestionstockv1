package com.gestionStocks.gestionStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "ventes")
@EqualsAndHashCode(callSuper = true)
public class Ventes extends  AbstractEntity{

    @Column(name = "datevente")
    private Instant dateVente;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @Column(name = "montantTotal")
    private BigDecimal montantTotal;

    @Column(name = "montantPaye")
    private BigDecimal montantPaye;

    @ManyToOne
    @JoinColumn(name = "idClient", nullable = false)
    private Client client ;

    @OneToMany(mappedBy = "ventes",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LigneVentes> ligneVentes;

    @OneToMany(mappedBy = "ventes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dette> listDette;
}
