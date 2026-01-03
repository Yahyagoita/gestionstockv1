package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.EntrepriseDto;
import com.gestionStocks.gestionStock.dto.RoleDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.Entreprise;
import com.gestionStocks.gestionStock.model.Role;
import com.gestionStocks.gestionStock.repository.RoleRepository;
import com.gestionStocks.gestionStock.service.RoleService;
import com.gestionStocks.gestionStock.validation.EntrepriseValidation;
import com.gestionStocks.gestionStock.validation.RoleValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public RoleDto save(RoleDto dto) {
        List<String> errors = RoleValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("Role is not Valid{}",dto);
            throw new InvalidEntityException("Role est invalid", ErrorCodes.ROLE_NOT_VALID,errors);
        }
        Optional<Role> roleExistant = roleRepository.findByNomRoleAndIdEntreprise(dto.getNomrole(), dto.getIdEntrepise());
        if (roleExistant.isPresent()){
            log.error("le role avec ce nom existe déjà"+dto.getNomrole());
            throw new EntityNotFoundException("le role avec ce nom existe déjà"+dto.getNomrole(),ErrorCodes.ENTREPRISE_NOT_VALID);
        }
        return RoleDto.fromEntity(
                roleRepository.save(RoleDto.toEntity(dto))
        );
    }

    @Override
    public RoleDto update(Integer id, RoleDto dto) {
        if (id == null){
            log.error("l'Id est null");
            throw new EntityNotFoundException("l'id est null");
        }
        List<String> errors = RoleValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("Role is not valid{}",dto);
            throw new InvalidEntityException("Entreprise est invalid", ErrorCodes.ROLE_NOT_VALID ,errors);
        }
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role est introuvable dans la BDD", ErrorCodes.ROLE_NOT_FOUND));

        Optional<Role> roleExistant = roleRepository.findByNomRoleAndIdEntreprise(dto.getNomrole(), dto.getIdEntrepise());
        if (roleExistant.isPresent()){
            log.error("le role avec ce nom existe déjà"+dto.getNomrole());
            throw new EntityNotFoundException("le role avec ce nom existe déjà"+dto.getNomrole(),ErrorCodes.ENTREPRISE_NOT_VALID);
        }

        role.setNomRole(dto.getNomrole());
        role.setIdEntreprise(dto.getIdEntrepise());
        return RoleDto.fromEntity(
                roleRepository.save(role)
        );
    }

    @Override
    public RoleDto findById(Integer id) {
        if (id == null){
            log.error("l'Id is null");
            throw new EntityNotFoundException("l'idRole est null");
        }
        return roleRepository.findById(id).map(RoleDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("le Role avec l'id "+id,ErrorCodes.ROLE_NOT_FOUND));
    }
    @Override
    public List<RoleDto> findByNomRoleAndIdEntreprise(String nom, Integer idEntreprise) {
        if (nom == null || idEntreprise == null){
            log.error("le nom ou l'identreprise est null");
            throw new EntityNotFoundException("le nom ou l'identreprise est null");
        }
        List<Role> listRoleEntreprise = roleRepository.findByIdEntrepriseAndNomRole(idEntreprise,nom);
        return listRoleEntreprise.stream().map(RoleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(RoleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> findAllByEntreprise(Integer idEntreprise) {
        return roleRepository.findByIdEntreprise(idEntreprise)
                .stream()
                .map(RoleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'Id is null");
            throw new EntityNotFoundException("l'id est null");
        }
        roleRepository.deleteById(id);
    }
}
