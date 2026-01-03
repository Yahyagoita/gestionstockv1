package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.EntrepriseDto;

import java.util.List;
import java.util.Optional;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto dto);
    List<EntrepriseDto> findByAll();
    EntrepriseDto findById(Integer id);
    EntrepriseDto update(Integer id, EntrepriseDto dto);
    void delete(Integer id);

}
