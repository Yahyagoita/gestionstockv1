package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.model.Client;
import com.gestionStocks.gestionStock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
   Optional<Client>  findByTel(String tel);
   List<Ventes> findByVentes(Integer id);

   List<Client> findByIdEntreprise(Integer id);
}
