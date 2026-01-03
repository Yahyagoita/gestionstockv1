package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.EntrepriseDto;
import com.gestionStocks.gestionStock.dto.RoleDto;
import com.gestionStocks.gestionStock.dto.UtilisateurDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.Entreprise;
import com.gestionStocks.gestionStock.model.Role;
import com.gestionStocks.gestionStock.model.Utilisateur;
import com.gestionStocks.gestionStock.repository.EntrepriseRepository;
import com.gestionStocks.gestionStock.repository.RoleRepository;
import com.gestionStocks.gestionStock.repository.UtilisateurRepository;
import com.gestionStocks.gestionStock.service.UtilisateurService;
import com.gestionStocks.gestionStock.validation.UtilisateurValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    private final EntrepriseRepository entrepriseRepository;

    private final RoleRepository roleRepository;
    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,EntrepriseRepository entrepriseRepository,RoleRepository roleRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("Utilisateur is invalid{}",dto);
            throw new InvalidEntityException("Utilisateur est invalide ", ErrorCodes.UTILISATEUR_NOT_VALID,errors);
        }
        Optional<Utilisateur> utilisateurExistant = utilisateurRepository.findByEmail(dto.getEmail());
        if (utilisateurExistant.isPresent()){
            log.error("l'utilisateur que vous voullez enregistre existe déjà{}",dto.getNom());
            throw new InvalidEntityException("l'utilisateur que vous voullez enregistre existe déjà", ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(UtilisateurDto.toEntity(dto))
        );
    }
    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll()
                .stream().map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est introuvable dans la BDD "+id,ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("l'id est introuvable dans la BDD "+id,ErrorCodes.UTILISATEUR_NOT_FOUND));
    }
    @Override
    public UtilisateurDto update(Integer id, UtilisateurDto dto) {
        if (id == null) {
            log.error("L'id est null");
            throw new EntityNotFoundException("L'id utilisateur est null", ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Utilisateur introuvable en BDD pour l'id {}", id);
                    return new EntityNotFoundException(
                            "Utilisateur introuvable pour l'id " + id,
                            ErrorCodes.UTILISATEUR_NOT_FOUND
                    );
                });

        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());

        if (dto.getRole() !=null){
            Role role = roleRepository.findById(dto.getRole())
                    .orElseThrow(() ->  new EntityNotFoundException(
                            "Role introuvable pour l'id " + dto.getRole(),
                            ErrorCodes.ENTREPRISE_NOT_FOUND
                    ) );
            utilisateur.setRole(role);
        }
        if (dto.getIdEntreprise() != null) {
            Entreprise entreprise = entrepriseRepository.findById(dto.getIdEntreprise())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Entreprise introuvable pour l'id " + dto.getIdEntreprise(),
                            ErrorCodes.ENTREPRISE_NOT_FOUND
                    ));
            utilisateur.setIdEntreprise(entreprise);
        }

        if (dto.getMotPasse() != null) {
           // utilisateur.setMotPasse(passwordEncoder.encode(dto.getMotPasse()));
        }

        return UtilisateurDto.fromEntity(utilisateurRepository.save(utilisateur));
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        if (!StringUtils.hasLength(email)){
            log.error("l'email is introuvable"+ email);
            throw new EntityNotFoundException("l'email est introuvable dans la BDD "+email,ErrorCodes.CATEGORY_NOT_FOUND);

        }
        return utilisateurRepository.findByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("l'email est introuvable dans la BDD "+email,ErrorCodes.CATEGORY_NOT_FOUND));
    }
    @Override
    public List<UtilisateurDto> findUtilisateurEntreprise(Integer idEntreprise) {
        if (idEntreprise == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est introuvable dans la BDD "+idEntreprise,ErrorCodes.CATEGORY_NOT_FOUND);
        }
        //Entreprise entreprise = entrepriseRepository.findById(idEntreprise).orElseThrow(() -> new EntityNotFoundException("l'entreprise avec l'id "+idEntreprise+" n'existe pas dans la BDD"));
        return utilisateurRepository.findByIdEntreprise_Id(idEntreprise).stream().map(UtilisateurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est introuvable dans la BDD "+id,ErrorCodes.CATEGORY_NOT_FOUND);
        }
        utilisateurRepository.deleteById(id);
    }
}
