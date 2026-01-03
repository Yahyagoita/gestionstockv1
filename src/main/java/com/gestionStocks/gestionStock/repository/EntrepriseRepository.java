package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise,Integer> {
    Optional<Entreprise> findByEmailAndNom(String email, String nom);
}
