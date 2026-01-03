package com.gestionStocks.gestionStock.service;

import com.gestionStocks.gestionStock.dto.EntrepriseDto;
import com.gestionStocks.gestionStock.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto save(RoleDto dto);
    RoleDto update(Integer id,RoleDto dto);
    RoleDto findById(Integer id);
    List<RoleDto> findByNomRoleAndIdEntreprise(String nom, Integer idEntreprise);
    List<RoleDto> findAll();
    List<RoleDto> findAllByEntreprise(Integer idEntreprise);
    void delete(Integer id);
}
