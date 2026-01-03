package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDto save(UtilisateurDto dto);
    List<UtilisateurDto> findAll();
    UtilisateurDto findById(Integer id);
    UtilisateurDto update(Integer id,UtilisateurDto dto);
    UtilisateurDto findByEmail(String email);
    List<UtilisateurDto> findUtilisateurEntreprise(Integer idEntreprise);
    void delete(Integer id);
}
