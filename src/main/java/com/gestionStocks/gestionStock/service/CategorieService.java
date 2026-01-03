package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.CategorieDto;

import java.util.List;

public interface CategorieService {

    CategorieDto save(CategorieDto dto);
    CategorieDto update(Integer id,CategorieDto dto);
    List<CategorieDto> findAll();
    List<CategorieDto> findByIdEntreprise(Integer idEntreprise);
    CategorieDto findById(Integer id);
    void delete(Integer id);

}
