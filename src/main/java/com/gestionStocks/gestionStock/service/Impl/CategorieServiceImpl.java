package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.CategorieDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.Categorie;
import com.gestionStocks.gestionStock.repository.CategorieRepository;
import com.gestionStocks.gestionStock.service.CategorieService;
import com.gestionStocks.gestionStock.validation.CategorieValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieServiceImpl(CategorieRepository categorieRepository){
        this.categorieRepository = categorieRepository;
    }
    @Override
    public CategorieDto save(CategorieDto dto) {

        List<String> errors = CategorieValidation.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Categorie is not valid {}", dto);
            throw new InvalidEntityException(
                    "Categorie est invalide",
                    ErrorCodes.CATEGORY_NOT_VALID,
                    errors
            );
        }
        Optional<Categorie> categorieExistante =
                categorieRepository.findByNomAndIdEntreprise(dto.getNom(), dto.getIdEntreprise());

        if (categorieExistante.isPresent()) {
            log.error("Categorie already exists with same name {} and idEntreprise {}", dto.getNom(), dto.getIdEntreprise());
            throw new InvalidEntityException(
                    "Une catégorie avec le même nom existe déjà dans cette entreprise",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE
            );
        }
        Categorie categorie = categorieRepository.save(CategorieDto.toEntity(dto));
        return CategorieDto.fromEntity(categorie);
    }


    @Override
    public CategorieDto update(Integer id,CategorieDto dto) {

        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null "+id,ErrorCodes.CATEGORY_NOT_FOUND);
        }
        List<String> errors = CategorieValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("Categorie is not valid{}",dto);
            throw new InvalidEntityException("Categorie est invalid", ErrorCodes.CATEGORY_NOT_VALID,errors);
        }
        Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("l'id est introuvable dans la BDD "+id,ErrorCodes.CATEGORY_NOT_FOUND));

        Optional<Categorie> categorieExistante =
                categorieRepository.findByNomAndIdEntreprise(dto.getNom(), dto.getIdEntreprise());

        if (categorieExistante.isPresent()) {
            log.error("Categorie already exists with same name {} and idEntreprise {}", dto.getNom(), dto.getIdEntreprise());
            throw new InvalidEntityException(
                    "Une catégorie avec le même nom existe déjà dans cette entreprise",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE
            );
        }
        categorie.setNom(dto.getNom());
        categorieRepository.save(categorie);
        return CategorieDto.fromEntity(categorie);
    }

    @Override
    public List<CategorieDto> findAll() {
        return categorieRepository.findAll().stream()
                .map(CategorieDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategorieDto> findByIdEntreprise(Integer idEntreprise) {
        if (idEntreprise == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null "+idEntreprise,ErrorCodes.CATEGORY_NOT_FOUND);
        }
        List<Categorie> categorieList = categorieRepository.findByIdEntreprise(idEntreprise);
        if (categorieList.isEmpty()){
            log.error("aucune Categorise n'a été trouver !!!");
            return null;
        }
        return categorieRepository.findByIdEntreprise(idEntreprise).stream().map(CategorieDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public CategorieDto findById(Integer id) {
        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null "+id,ErrorCodes.CATEGORY_NOT_FOUND);
        }
        return categorieRepository.findById(id).map(CategorieDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("l'id est introuvable dans la BDD "+id,ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est introuvable dans la BDD "+id,ErrorCodes.CATEGORY_NOT_FOUND);
        }
        categorieRepository.deleteById(id);
    }
}
