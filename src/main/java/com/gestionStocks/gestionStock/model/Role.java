package com.gestionStocks.gestionStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Entity
@Data
@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity{

    @Column(name = "nomrole")
    private String nomRole;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Utilisateur> utilisateurs;
}
