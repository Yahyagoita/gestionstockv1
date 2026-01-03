package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.MvtStckDto;
import com.gestionStocks.gestionStock.model.TypeMvt;

import java.util.List;

public interface MvtStckService {
    MvtStckDto saveEntreeSORTIE(MvtStckDto dto);
    MvtStckDto updateEntreeSortie(Integer id,MvtStckDto dto);
    List<MvtStckDto> findAllByEntreprise(Integer idEntreprise);
    List<MvtStckDto> findAllByEntrepriseByType(Integer idEntreprise, TypeMvt typeMvt);
    void delete(Integer id);

}
