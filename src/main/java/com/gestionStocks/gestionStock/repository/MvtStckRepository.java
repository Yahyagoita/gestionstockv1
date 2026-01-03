package com.gestionStocks.gestionStock.repository;

import com.gestionStocks.gestionStock.model.MvtStck;
import com.gestionStocks.gestionStock.model.TypeMvt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gestionStocks.gestionStock.model.TypeMvt.ENTREE;

@Repository
public interface MvtStckRepository extends JpaRepository<MvtStck,Integer> {
    List<MvtStck> findByIdEntreprise(Integer idEntreprise);

    List<MvtStck> findByIdEntrepriseAndTypeMvt(Integer idEntreprise, TypeMvt type);
}
