package com.gestionStocks.gestionStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @CreatedDate
    @Column(name = "dateCreation", nullable = false)
    @JsonIgnore
    private Instant creationDate;

    @LastModifiedDate
    @Column(name = "dateModification", nullable = false)
    @JsonIgnore
    private  Instant dateModification;
}
