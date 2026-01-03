package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.dto.ArticleDto;
import com.gestionStocks.gestionStock.model.Article;
import com.gestionStocks.gestionStock.model.LigneVentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Optional<Article> findByCodeArticleAndIdEntreprise(String code,Integer idEntre);
    Optional<Article> findByNomAndIdEntreprise(String nom,Integer idEntre);
    List<LigneVentes> findByligneVentes(Integer idArticle, Integer idEntreprise);
    List<Article> findAllByIdEntreprise(Integer idEntreprise);
    List<Article> findByIdCategorie_IdAndIdEntreprise(Integer idCategories, Integer idEntreprise);
    Optional<Article> findByNomAndCodeArticleAndIdEntreprise(String nom, String code, Integer idEntreprise);
}
