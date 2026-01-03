package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.ArticleDto;
import com.gestionStocks.gestionStock.dto.LigneVentesDto;
import com.gestionStocks.gestionStock.dto.MvtStckDto;
import com.gestionStocks.gestionStock.dto.VentesDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.*;
import com.gestionStocks.gestionStock.repository.*;
import com.gestionStocks.gestionStock.service.MvtStckService;
import com.gestionStocks.gestionStock.service.VenteService;
import com.gestionStocks.gestionStock.validation.VentesValidation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VenteService {

    private final ArticleRepository articleRepository;
    private final VentesRepository ventesRepository;
    private final ClientRepository clientRepository;
    private final DetteRepository detteRepository;
    private final MvtStckService mvtStckService;
    private final LigneVentesRepository ligneVentesRepository;

    @Autowired
    public VentesServiceImpl(
            VentesRepository ventesRepository,ArticleRepository articleRepository
            ,LigneVentesRepository ligneVentesRepository,MvtStckService mvtStckService
            ,ClientRepository clientRepository, DetteRepository detteRepository )
    {
        this.ventesRepository= ventesRepository;
        this.articleRepository = articleRepository;
        this.ligneVentesRepository = ligneVentesRepository;
        this.mvtStckService = mvtStckService;
        this.clientRepository = clientRepository;
        this.detteRepository = detteRepository;
    }
    @Override
    @Transactional
    public VentesDto save(VentesDto dto) {
        dto.setDateVente(Instant.now());
        List<String> errors = VentesValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("vente is not valid{}",dto);
            throw new InvalidEntityException("la vente n'est pas valide ", ErrorCodes.VENTE_NOT_VALID,errors);
        }

        List<String> articleErrors = new ArrayList<>();

        dto.getLigneVentes().forEach(ligne ->{
            Optional<Article> article = articleRepository.findById(ligne.getArticle());
            if (article.isEmpty()){
                log.error("l'id is null");
                articleErrors.add("L'article avec l'ID " + ligne.getArticle() + " n'existe pas");
            }
        });

        if (!articleErrors.isEmpty()){
            log.error("one or more article not found in the BDD{}",errors);
            throw new InvalidEntityException("Une ou plusieur article n'a pas été dans la BDD", ErrorCodes.VENTE_NOT_FOUND, articleErrors);
        }

        Ventes ventesSaved = ventesRepository.save(VentesDto.toEntity(dto));
        BigDecimal montantTotal = BigDecimal.ZERO;

        for (LigneVentesDto ligneDto : dto.getLigneVentes()) {

            Article article = articleRepository.findById(ligneDto.getArticle())
                    .orElseThrow(() ->
                            new EntityNotFoundException("l'article avec l'id "
                                    + ligneDto.getArticle() + " n'existe pas dans la BDD")
                    );

            BigDecimal pu;
            if (ligneDto.getPrixUnitaireDefaut() == null
                    || ligneDto.getPrixUnitaireDefaut().compareTo(BigDecimal.ZERO) == 0) {
                pu = article.getPrixUnitaire();
            } else {
                pu = ligneDto.getPrixUnitaireDefaut();
            }

            BigDecimal montant = calcul(ligneDto.getQuantite(), pu);

            montantTotal = montantTotal.add(montant);

            LigneVentes ligneVentes = LigneVentesDto.toEntity(ligneDto);
            ligneVentes.setVentes(ventesSaved);
            ligneVentes.setIdEntreprise(dto.getIdEntreprise());
            ligneVentes.setArticle(article);
            ligneVentes.setPrixUnitaireDefaut(pu);
            ligneVentes.setMontant(montant);

            ligneVentesRepository.save(ligneVentes);
            sortieVenteMvtStck(ligneVentes);
        }

        ventesSaved.setMontantTotal(montantTotal);

        if (dto.getMontantPaye().abs().compareTo(montantTotal) < 0){
            BigDecimal restant = montantTotal.subtract(dto.getMontantPaye().abs());

            Client clientVerification = clientRepository.findByTel(dto.getTelClient())
                    .orElseGet(() -> {
                        Client client = new Client();
                        client.setNomClient(dto.getNomClient());
                        client.setTelClient(dto.getTelClient());
                        client.setIdEntreprise(dto.getIdEntreprise());
                        return clientRepository.save(client);
                    });

            Dette dette = new Dette();
            dette.setClient(clientVerification);
            dette.setVentes(ventesSaved);
            dette.setDate(Instant.now());
            dette.setRecouvrement(dto.getMontantPaye());
            dette.setMontantRestant(restant);
            detteRepository.save(dette);
        }else {
            ventesSaved.setClient(null);
        }
        ventesRepository.save(ventesSaved);

        return VentesDto.fromEntity(ventesSaved);
    }

    @Override
    public VentesDto update(Integer id, VentesDto dto) {

        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null");
        }
        Ventes ventesExistant = ventesRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("la vente que voullez modifier n'existe pas dans la BDD"));

        List<LigneVentes> ancienLigneVente =
                ligneVentesRepository.findAllByVentes(ventesExistant);
        ancienLigneVente.forEach(ligneVentes -> {
            mvtStckService.delete(ligneVentes.getId());
        });
        ligneVentesRepository.deleteAll(ancienLigneVente);

        List<String> errors = VentesValidation.validate(dto);
        if (!errors.isEmpty()){
            log.error("vente is not valid{}",dto);
            throw new InvalidEntityException("la vente n'est pas valide ", ErrorCodes.VENTE_NOT_VALID,errors);
        }

        List<String> articleErrors = new ArrayList<>();

        dto.getLigneVentes().forEach(ligne ->{
            Optional<Article> article = articleRepository.findById(ligne.getArticle());
            if (article.isEmpty()){
                log.error("l'id is null");
                articleErrors.add("L'article avec l'ID " + ligne.getArticle() + " n'existe pas");
            }
        });

        if (!articleErrors.isEmpty()){
            log.error("one or more article not found in the BDD{}",articleErrors);
            throw new InvalidEntityException("Une ou plusieur article n'a pas été dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }
        ventesExistant.setIdEntreprise(dto.getIdEntreprise());
        ventesExistant.setCommentaire(dto.getCommentaire());
        Ventes ventesSaved = ventesRepository.save(ventesExistant);

        BigDecimal montantTotal = BigDecimal.ZERO;

        for (LigneVentesDto ligneDto : dto.getLigneVentes()) {

            Article article = articleRepository.findById(ligneDto.getArticle())
                    .orElseThrow(() ->
                            new EntityNotFoundException("l'article avec l'id "
                                    + ligneDto.getArticle() + " n'existe pas dans la BDD")
                    );

            BigDecimal pu;
            if (ligneDto.getPrixUnitaireDefaut() == null
                    || ligneDto.getPrixUnitaireDefaut().compareTo(BigDecimal.ZERO) == 0) {
                pu = article.getPrixUnitaire();
            } else {
                pu = ligneDto.getPrixUnitaireDefaut();
            }

            BigDecimal montant = calcul(ligneDto.getQuantite(), pu);

            montantTotal = montantTotal.add(montant);

            LigneVentes ligneVentes = LigneVentesDto.toEntity(ligneDto);
            ligneVentes.setVentes(ventesSaved);
            ligneVentes.setIdEntreprise(dto.getIdEntreprise());
            ligneVentes.setArticle(article);
            ligneVentes.setPrixUnitaireDefaut(pu);
            ligneVentes.setMontant(montant);

            ligneVentesRepository.save(ligneVentes);
            sortieVenteMvtStck(ligneVentes);
        }

        if (dto.getMontantPaye().abs().compareTo(montantTotal) < 0){
            BigDecimal restant = montantTotal.subtract(dto.getMontantPaye().abs());

            Client clientVerification = clientRepository.findByTel(dto.getTelClient())
                    .orElseGet(() -> {
                        Client client = new Client();
                        client.setNomClient(dto.getNomClient());
                        client.setTelClient(dto.getTelClient());
                        client.setIdEntreprise(dto.getIdEntreprise());
                        return clientRepository.save(client);
                    });

            Dette dette = new Dette();
            dette.setClient(clientVerification);
            dette.setVentes(ventesSaved);
            dette.setDate(Instant.now());
            dette.setRecouvrement(dto.getMontantPaye());
            dette.setMontantRestant(restant);
            detteRepository.save(dette);
        }else {
            ventesSaved.setClient(null);
        }
        ventesSaved.setMontantTotal(montantTotal);
        ventesRepository.save(ventesSaved);

        return VentesDto.fromEntity(
                ventesSaved
        );
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll()
                .stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentesDto> findAllByEntreprise(Integer id) {
        return ventesRepository.findByIdEntreprise(id)
                .stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public List<LigneVentesDto> historique(Integer id) {
        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null");
        }
        return ligneVentesRepository.findByIdEntreprise(id)
                .stream()
                .map(LigneVentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null");
        }
        Ventes ventes = ventesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("la vente que voullez supprimer est introuvable dans BDD",ErrorCodes.VENTE_NOT_FOUND));
        List<LigneVentes> ligneVentes = ligneVentesRepository.findAllByVentes(ventes);
        ligneVentes.forEach(ligneVentes1 -> {
            mvtStckService.delete(ligneVentes1.getId());
            ligneVentesRepository.deleteById(ligneVentes1.getId());
        });
        ventesRepository.deleteById(ventes.getId());

    }
    private BigDecimal calcul(BigDecimal qte, BigDecimal pu){
        BigDecimal montant = qte.multiply(pu);
        return montant;
    }

    private void sortieVenteMvtStck(LigneVentes lig){
        MvtStckDto mvtStckDto = MvtStckDto.builder()
                .typeMvt(TypeMvt.SORTIE)
                .dateMvt(Instant.now())
                .idEntreprise(lig.getIdEntreprise())
                .quantite(lig.getQuantite())
                .article(lig.getArticle().getId())
                .build();
        mvtStckService.saveEntreeSORTIE(mvtStckDto);
    }

}
