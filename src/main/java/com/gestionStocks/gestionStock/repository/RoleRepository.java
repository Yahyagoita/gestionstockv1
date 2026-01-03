package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    List<Role> findByIdEntrepriseAndNomRole(Integer idEntreprise,String nom);
    List<Role> findByIdEntreprise(Integer idEntreprise);
    Optional<Role> findByNomRoleAndIdEntreprise(String nom, Integer idEntreprise);
}
