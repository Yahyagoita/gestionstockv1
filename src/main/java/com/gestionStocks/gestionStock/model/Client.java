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
@Table(name = "client")
@EqualsAndHashCode(callSuper = true)
public class Client extends AbstractEntity {

    @Column(name = "nom")
    private String nomClient;

    @Column(name = "tel")
    private String telClient;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dette> listDette;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ventes> listVentes;
}
