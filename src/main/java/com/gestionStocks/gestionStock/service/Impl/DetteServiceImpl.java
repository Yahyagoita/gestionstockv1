package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.DetteDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.Client;
import com.gestionStocks.gestionStock.model.Dette;
import com.gestionStocks.gestionStock.model.Ventes;
import com.gestionStocks.gestionStock.repository.ClientRepository;
import com.gestionStocks.gestionStock.repository.DetteRepository;
import com.gestionStocks.gestionStock.repository.VentesRepository;
import com.gestionStocks.gestionStock.service.DetteService;
import com.gestionStocks.gestionStock.validation.DetteValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DetteServiceImpl implements DetteService {
    private final DetteRepository detteRepository;
    private final ClientRepository clientRepository;
    private final VentesRepository ventesRepository;
    public DetteServiceImpl(DetteRepository detteRepository, ClientRepository clientRepository,VentesRepository ventesRepository){
        this.detteRepository = detteRepository;
        this.clientRepository = clientRepository;
        this.ventesRepository = ventesRepository;
    }
    @Override
    public DetteDto recouvrement(DetteDto dto) {
        Client client = clientRepository.findById(dto.getClient()).orElseThrow(() -> new EntityNotFoundException("Client introuvable dans la BDD"));
        Ventes ventes = ventesRepository.findById(dto.getVentes()).orElseThrow(() -> new EntityNotFoundException("Vente introuvable dans la BDD"));
        BigDecimal recouvrement = ventes.getMontantPaye().add(dto.getRecouvrement().abs());
        BigDecimal restant = ventes.getMontantTotal().subtract(recouvrement);
        ventes.setMontantPaye(recouvrement);
        dto.setMontantRestant(restant);
        return DetteDto.fromEntity(
                detteRepository.save(DetteDto.ToEntity(dto))
        );
    }

    @Override
    public DetteDto updaterecouvrement(Integer id,DetteDto dto) {
        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null", ErrorCodes.DETTE_NOT_FOUND );
        }
        Dette dette = detteRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("recouvrement introuvable dans la DBB"));
        Ventes ventes = detteRepository.findByVentes(dette.getVentes()).orElseThrow(() ->
                new EntityNotFoundException("Ventes introuvable dans la BDD"));
        BigDecimal existant = ventes.getMontantPaye().subtract(dette.getRecouvrement());
        ventes.setMontantPaye(existant.add(dto.getRecouvrement()));
        dette.setDate(Instant.now());
        dette.setRecouvrement(dto.getRecouvrement());
        dette.setMontantRestant(ventes.getMontantTotal().subtract(dto.getRecouvrement()));
        ventesRepository.save(ventes);
        return DetteDto.fromEntity(
                detteRepository.save(dette)
        );
    }

    @Override
    public DetteDto findByIdclientAndIdEntreprise(Integer idClient, Integer idEntre) {
        if (idClient == null || idEntre ==null){
            log.error("l'id du client ou de l'entreprise is null");
            throw new EntityNotFoundException("l'id du client ou de l'entreprise", ErrorCodes.DETTE_NOT_FOUND );
        }
        return detteRepository.findByIdclientAndIdEntreprise(idClient, idEntre).map(DetteDto::fromEntity).orElseThrow(
                () -> new EntityNotFoundException("le client est introuvable dans la BDD")
        );
    }
    @Override
    public List<DetteDto> findAllEntreprise(Integer idEntre) {
        if (idEntre ==null){
            log.error("l'id de l'entreprise is null");
            throw new EntityNotFoundException("l'id de l'entreprise", ErrorCodes.DETTE_NOT_FOUND);
        }
        return detteRepository.findAllByIdEntreprise(idEntre)
                .stream()
                .map(DetteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id ==null){
            log.error("l'id de l'entreprise is null");
            throw new EntityNotFoundException("l'id de l'entreprise", ErrorCodes.DETTE_NOT_FOUND);
        }
        Dette dette = detteRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("recouvrement introuvable dans la DBB"));
        Ventes ventes = detteRepository.findByVentes(dette.getVentes()).orElseThrow(() ->
                new EntityNotFoundException("Ventes introuvable dans la BDD"));
        ventes.setMontantPaye(ventes.getMontantPaye().subtract(dette.getRecouvrement()));
        ventesRepository.save(ventes);
        detteRepository.deleteById(id);
    }
}
