package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByIdEntreprise_Id(Integer idEntreprise);
}
