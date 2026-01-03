package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.model.Dette;
import com.gestionStocks.gestionStock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetteRepository extends JpaRepository<Dette,Integer> {
    Optional<Dette> findByClient_IdAndIdEntreprise(Integer idClient, Integer idEntreprise);
    List<Dette> findAllByIdEntreprise(Integer idEntreprise);
    Optional<Ventes> findByVentes(Ventes ventes);
}
