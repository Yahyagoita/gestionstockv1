package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.dto.LigneVentesDto;
import com.gestionStocks.gestionStock.model.LigneVentes;
import com.gestionStocks.gestionStock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneVentesRepository extends JpaRepository<LigneVentes,Integer> {
    List<LigneVentes> findByIdEntreprise(Integer id);
    List<LigneVentes> findAllByVentes(Ventes ventes);
    List<LigneVentes> findByArticle_IdAndIdEntreprise(Integer idArticle, Integer idEntreprise);
}
