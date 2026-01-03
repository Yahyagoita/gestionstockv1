package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.LigneVentesDto;
import com.gestionStocks.gestionStock.dto.VentesDto;

import java.util.List;

public interface VenteService {
    VentesDto save(VentesDto dto);

    VentesDto update(Integer id,VentesDto dto);
    List<VentesDto> findAll();
    List<VentesDto> findAllByEntreprise(Integer id);
    List<LigneVentesDto> historique(Integer id);
    void delete (Integer id);
}
