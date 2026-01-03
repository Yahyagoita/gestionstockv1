package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.dto.CategorieDto;
import com.gestionStocks.gestionStock.model.Article;
import com.gestionStocks.gestionStock.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Integer> {

    Optional<Categorie> findByNomAndIdEntreprise(String nom, Integer idEntreprise);

    List<Categorie> findByIdEntreprise(Integer idEntreprise);
}
