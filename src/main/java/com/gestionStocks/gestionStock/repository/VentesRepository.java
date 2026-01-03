package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentesRepository extends JpaRepository<Ventes,Integer> {
    List<Ventes> findByIdEntreprise(Integer id);
    List<Ventes> findByClient_Id(Integer id);

}
