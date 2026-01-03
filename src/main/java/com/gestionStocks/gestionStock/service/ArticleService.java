package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.ArticleDto;
import com.gestionStocks.gestionStock.dto.LigneVentesDto;
import com.gestionStocks.gestionStock.model.LigneVentes;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto dto);
    ArticleDto update(Integer idArticle, ArticleDto dto);
    ArticleDto findById(Integer id);
    ArticleDto findByCodeArticleAndIdEntreprise(String codeArticle,Integer idEntre);
    ArticleDto findByNomAndIdEntreprise(String nom,Integer idEntre);
    List<ArticleDto> findAll();
    List<ArticleDto> findAllByEntreprise(Integer idEntre);
    List<ArticleDto> findAllEntre(Integer id);

    List<ArticleDto> findAllArticleByIdCategory(Integer idCategory, Integer idEntreprise);
    List<LigneVentesDto> findHistoriqueVentes(Integer idArticle, Integer idEntreprise);

    void delete(Integer id, Integer idEntreprise);
}
