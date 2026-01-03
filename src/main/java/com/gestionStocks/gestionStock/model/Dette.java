package com.gestionStocks.gestionStock.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Table(name = "dette")
@EqualsAndHashCode(callSuper = true)
public class Dette extends AbstractEntity{

    @Column(name = "restant")
    private BigDecimal montantRestant;

    @Column(name = "recouvrement")
    private BigDecimal recouvrement;

    @Column(name = "dateVersement")
    private Instant date;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "idVentes")
    private Ventes ventes;
}
