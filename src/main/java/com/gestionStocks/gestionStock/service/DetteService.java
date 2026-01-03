package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.DetteDto;
import com.gestionStocks.gestionStock.dto.EntrepriseDto;

import java.util.List;

public interface DetteService {
    DetteDto recouvrement(DetteDto dto);
    DetteDto updaterecouvrement(Integer id,DetteDto dto);
    DetteDto findByIdclientAndIdEntreprise(Integer idClient, Integer idEntre);
    List<DetteDto> findAllEntreprise(Integer idEntre);
    void delete(Integer id);
}
