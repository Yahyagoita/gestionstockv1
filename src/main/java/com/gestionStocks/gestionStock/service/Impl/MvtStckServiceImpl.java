package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.MvtStckDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.Article;
import com.gestionStocks.gestionStock.model.MvtStck;
import com.gestionStocks.gestionStock.model.TypeMvt;
import com.gestionStocks.gestionStock.repository.ArticleRepository;
import com.gestionStocks.gestionStock.repository.MvtStckRepository;
import com.gestionStocks.gestionStock.service.MvtStckService;
import com.gestionStocks.gestionStock.validation.MvtSckValidation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MvtStckServiceImpl implements MvtStckService {

    private final MvtStckRepository mvtStckRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public MvtStckServiceImpl(MvtStckRepository mvtStckRepository,ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
        this.mvtStckRepository = mvtStckRepository;
    }
    @Override
    public MvtStckDto saveEntreeSORTIE(MvtStckDto dto) {
        dto.setDateMvt(Instant.now());
            List<String> errors = MvtSckValidation.validate(dto);
            if (!errors.isEmpty()) {
                throw new InvalidEntityException("Mouvement non valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
            }

            Article article = articleRepository.findById(dto.getArticle())
                    .orElseThrow(() -> new EntityNotFoundException("Article introuvable"));

            if (dto.getTypeMvt() == TypeMvt.ENTREE) {
                article.setQuantite(article.getQuantite().add(dto.getQuantite().abs()));
            }

            if (dto.getTypeMvt() == TypeMvt.SORTIE) {
                if (article.getQuantite().compareTo(dto.getQuantite()) < 0) {
                    throw new InvalidEntityException("Stock insuffisant");
                }
                article.setQuantite(article.getQuantite().subtract(dto.getQuantite().abs()));
            }

            articleRepository.save(article);

            return MvtStckDto.fromEntity(
                    mvtStckRepository.save(MvtStckDto.toEntity(dto))
            );
        }
    @Override
    public MvtStckDto updateEntreeSortie(Integer id, MvtStckDto dto) {
        if (id == null){
            log.error("l'id is null");
            throw new InvalidEntityException("l'id est null{}",ErrorCodes.MVT_STK_NOT_VALID);
        }

        List<String> errors = MvtSckValidation.validate(dto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Mouvement non valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }

        MvtStck mvtStck = mvtStckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mouvement introuvable dans la BDD",ErrorCodes.MVT_STK_NOT_FOUND));
        BigDecimal qtiteOld = mvtStck.getQuantite();
        mvtStck.setQuantite(dto.getQuantite().abs());

        if (!mvtStck.getArticle().getId().equals(dto.getArticle())) {
            throw new InvalidEntityException("Impossible de changer l’article d’un mouvement !");
        }

        if (!dto.getTypeMvt().equals(mvtStck.getTypeMvt())) {
            throw new InvalidEntityException("On ne peut pas changer le type d’un mouvement (ENTREE ↔ SORTIE)");
        }

        Article article = articleRepository.findById(dto.getArticle())
                .orElseThrow(() -> new EntityNotFoundException("Article introuvable dans la BDD",ErrorCodes.ARTICLE_NOT_FOUND));

        if (dto.getTypeMvt() == TypeMvt.ENTREE) {
            article.setQuantite(article.getQuantite().add(dto.getQuantite().abs()).subtract(qtiteOld));
        }

        if (dto.getTypeMvt() == TypeMvt.SORTIE) {
            if (article.getQuantite().compareTo(dto.getQuantite().abs()) < 0) {
                throw new InvalidEntityException("Stock insuffisant");
            }
            article.setQuantite(article.getQuantite().subtract(dto.getQuantite()).add(qtiteOld));
        }
        articleRepository.save(article);
        return MvtStckDto.fromEntity(
                mvtStckRepository.save(mvtStck)
        );
    }

    @Override
    public List<MvtStckDto> findAllByEntreprise(Integer idEntreprise) {
        return mvtStckRepository.findByIdEntreprise(idEntreprise)
                .stream().map(MvtStckDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<MvtStckDto> findAllByEntrepriseByType(Integer idEntreprise, TypeMvt typeMvt) {
             return mvtStckRepository.findByIdEntrepriseAndTypeMvt(idEntreprise, typeMvt)
                     .stream().map(MvtStckDto::fromEntity)
                     .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'id is null");
            throw new InvalidEntityException("l'id est null{}",ErrorCodes.MVT_STK_NOT_VALID);
        }
        MvtStck mvtStck = mvtStckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mouvement introuvable dans la BDD",ErrorCodes.MVT_STK_NOT_FOUND));
        BigDecimal qtiteOld = mvtStck.getQuantite();

        Article article = articleRepository.findById(mvtStck.getArticle().getId())
                .orElseThrow(() -> new EntityNotFoundException("Article introuvable dans la BDD",ErrorCodes.ARTICLE_NOT_FOUND));

        if (mvtStck.getTypeMvt() == TypeMvt.ENTREE) {
            article.setQuantite(article.getQuantite().subtract(qtiteOld));
        }

        if (mvtStck.getTypeMvt() == TypeMvt.SORTIE) {
            article.setQuantite(article.getQuantite().add(qtiteOld));
        }
        articleRepository.save(article);
        mvtStckRepository.deleteById(id);
    }
}
