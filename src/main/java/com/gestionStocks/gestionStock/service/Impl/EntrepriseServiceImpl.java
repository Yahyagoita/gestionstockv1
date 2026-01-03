package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.EntrepriseDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.Entreprise;
import com.gestionStocks.gestionStock.repository.EntrepriseRepository;
import com.gestionStocks.gestionStock.service.EntrepriseService;
import com.gestionStocks.gestionStock.validation.CategorieValidation;
import com.gestionStocks.gestionStock.validation.EntrepriseValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;
    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository){
        this.entrepriseRepository = entrepriseRepository;
    }
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {

        List<String> errors = EntrepriseValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("Entrepise is not valid{}",dto);
            throw new InvalidEntityException("Entreprise est invalid", ErrorCodes.ENTREPRISE_NOT_VALID ,errors);
        }
        Optional<Entreprise> entrepriseExistant = entrepriseRepository.findByEmailAndNom(dto.getEmail(), dto.getNom());
        if (entrepriseExistant.isPresent()){
            log.error("Entreprise already exists with same name {} and idEntreprise {}", dto.getNom(), dto.getEmail());
            throw new InvalidEntityException(
                    "Une eEntreprise avec le même nom existe déjà dans cette entreprise et le même Email",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE
            );
        }
        return EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(dto))
        );
    }

    @Override
    public List<EntrepriseDto> findByAll() {
        return entrepriseRepository.findAll().stream().map(EntrepriseDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null){
            log.error("l'Id est null");
            throw new EntityNotFoundException("l'id est null");
        }
        return entrepriseRepository.findById(id).map(EntrepriseDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("l'Entreprise est introuvable dans la BDD avec l'id "+id,ErrorCodes.ENTREPRISE_NOT_FOUND));
    }

    @Override
    public EntrepriseDto update(Integer id, EntrepriseDto dto) {
        if (id == null){
            log.error("l'Id est null");
            throw new EntityNotFoundException("l'id est null");
        }
        List<String> errors = EntrepriseValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("Entrepise is not valid{}",dto);
            throw new InvalidEntityException("Entreprise est invalid", ErrorCodes.ENTREPRISE_NOT_VALID ,errors);
        }
        Entreprise entreprise = entrepriseRepository.findById(id).orElseThrow(() -> new InvalidEntityException("Entreprise est invalid", ErrorCodes.ENTREPRISE_NOT_FOUND));

        Optional<Entreprise> entrepriseExistant = entrepriseRepository.findByEmailAndNom(dto.getEmail(), dto.getNom());
        if (entrepriseExistant.isPresent()){
            log.error("Entreprise already exists with same name {} and idEntreprise {}", dto.getNom(), dto.getEmail());
            throw new InvalidEntityException(
                    "Une eEntreprise avec le même nom existe déjà dans cette entreprise et le même Email",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE
            );
        }
        entreprise.setNom(dto.getNom());
        entreprise.setEmail(dto.getEmail());
        entreprise.setTel(dto.getTel());
        //entreprise.setMotPasse(passwordEncoder.encode(dto.getMotPasse()));
        return EntrepriseDto.fromEntity(
                entrepriseRepository.save(entreprise)
        );
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'Id est null");
            throw new EntityNotFoundException("l'id est null");
        }
        entrepriseRepository.deleteById(id);
    }
}
