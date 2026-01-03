package com.gestionStocks.gestionStock.service.Impl;

import com.gestionStocks.gestionStock.dto.ArticleDto;
import com.gestionStocks.gestionStock.dto.CategorieDto;
import com.gestionStocks.gestionStock.dto.LigneVentesDto;
import com.gestionStocks.gestionStock.exception.EntityNotFoundException;
import com.gestionStocks.gestionStock.exception.ErrorCodes;
import com.gestionStocks.gestionStock.exception.InvalidEntityException;
import com.gestionStocks.gestionStock.model.Article;
import com.gestionStocks.gestionStock.model.Categorie;
import com.gestionStocks.gestionStock.model.LigneVentes;
import com.gestionStocks.gestionStock.repository.ArticleRepository;
import com.gestionStocks.gestionStock.repository.CategorieRepository;
import com.gestionStocks.gestionStock.repository.LigneVentesRepository;
import com.gestionStocks.gestionStock.service.ArticleService;
import com.gestionStocks.gestionStock.validation.ArticleValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final LigneVentesRepository ligneVentesRepository;

    private final CategorieRepository categorieRepository;
    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,LigneVentesRepository ligneVentesRepository,CategorieRepository categorieRepository){
        this.articleRepository = articleRepository;
        this.ligneVentesRepository = ligneVentesRepository;
        this.categorieRepository = categorieRepository;
    }
    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidation.validate(dto);
        if(!errors.isEmpty()){
            log.error("L'article is not valid {}",dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID,errors);
        }
       Optional<Article> article = articleRepository.findByNomAndCodeArticleAndIdEntreprise(dto.getNom(), dto.getCodeArticle(), dto.getIdEntreprise());
        if (article.isPresent()){
            log.error("l'article avec le nom "+dto.getNom() +" ,de code: "+dto.getCodeArticle()+" existe déjà !!!");
            throw new InvalidEntityException("l'article avec le nom "+dto.getNom() +" ,de code: "+dto.getCodeArticle()+" existe déjà !!!",ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        return ArticleDto.fromEntity(articleRepository.save(ArticleDto.toEntity(dto)));
    }
    @Override
    public ArticleDto update(Integer idArticle, ArticleDto dto) {
        Article articleUpdate = articleRepository.findById(idArticle).orElseThrow(() -> new EntityNotFoundException("larticle que vous voullez modifier est introuvable dans BDD "));

        List<String> errors = ArticleValidation.validate(dto);
        if(!errors.isEmpty()){
            log.error("L'article is not valid {}",dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID,errors);
        }
        /*
        Optional<Article> article = articleRepository.findByNomAndCodeArticleAndIdEntreprise(dto.getNom(), dto.getCodeArticle(), dto.getIdEntreprise());
        if (article.isPresent()){
            log.error("l'article avec le nom "+dto.getNom() +" ,de code: "+dto.getCodeArticle()+" existe déjà !!!");
            throw new InvalidEntityException("l'article avec le nom "+dto.getNom() +" ,de code: "+dto.getCodeArticle()+" existe déjà !!!",ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
         */
        articleUpdate.setNom(dto.getNom());
        articleUpdate.setCodeArticle(dto.getCodeArticle());
        articleUpdate.setQuantite(dto.getQuantite());
        articleUpdate.setPrixUnitaire(dto.getPrixUnitaire());
        articleUpdate.setDespcrition(dto.getDespcrition());
        Categorie cat = new Categorie();
        cat.setId(dto.getCategorie());
        articleUpdate.setIdCategorie(cat);

        Optional<Article> articleverification = articleRepository.findByNomAndCodeArticleAndIdEntreprise(dto.getNom(), dto.getCodeArticle(), dto.getIdEntreprise());
        if (articleverification.isPresent()){
            log.error("l'article avec le nom "+dto.getNom() +" ,de code: "+dto.getCodeArticle()+" existe déjà !!!");
            throw new InvalidEntityException("l'article avec le nom "+dto.getNom() +" ,de code: "+dto.getCodeArticle()+" existe déjà !!!",ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        return ArticleDto.fromEntity(articleRepository.save(articleUpdate));
    }

    @Override
        public ArticleDto findById(Integer id) {
            if (id == null){
                log.error("L'id is null");
                throw new EntityNotFoundException("l'article avec l'Id "+id+"est indisponible dans la BDD",ErrorCodes.ARTICLE_NOT_FOUND);
            }
            return articleRepository.findById(id)
                    .map(ArticleDto::fromEntity)
                    .orElseThrow(() -> new EntityNotFoundException("l'article avec l'Id "+id+"est indisponible dans la BDD",ErrorCodes.ARTICLE_NOT_FOUND));
        }

    @Override
    public ArticleDto findByCodeArticleAndIdEntreprise(String codeArticle, Integer idEntre) {
        if (!StringUtils.hasLength(codeArticle) || idEntre == null){
            log.error("code or l'id is null");
            return null;
        }
        return articleRepository.findByCodeArticleAndIdEntreprise(codeArticle,idEntre)
                .map(ArticleDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("l'article avec le code "+codeArticle+" est indisponible dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleDto findByNomAndIdEntreprise(String nom, Integer idEntre) {
        if (!StringUtils.hasLength(nom) || idEntre == null){
            log.error("nom or l'id is null");
            return null;
        }
        return articleRepository.findByCodeArticleAndIdEntreprise(nom,idEntre)
                .map(ArticleDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("l'article avec le nom "+nom+" est indisponible dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllByEntreprise(Integer idEntre) {
        if (idEntre ==null){
            log.error("l'id is null");
        }
        return articleRepository.findAllByIdEntreprise(idEntre).stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
    }
    @Override
    public List<ArticleDto> findAllEntre(Integer id) {
        return  articleRepository.findAllByIdEntreprise(id).stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategorie, Integer idEntreprise) {
        if (idCategorie == null || idEntreprise == null){
            log.error("l'idCategorie ou l'idEntreprise is null");
            throw new EntityNotFoundException("l'id est null");
        }
        return articleRepository.findByIdCategorie_IdAndIdEntreprise(idCategorie, idEntreprise)
                .stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVentesDto> findHistoriqueVentes(Integer idArticle,Integer idEntreprise) {

        if (idArticle == null){
            log.error("l'id is null");
            throw new EntityNotFoundException("l'id est null");
        }
        return ligneVentesRepository.findByArticle_IdAndIdEntreprise(idArticle,idEntreprise)
                .stream()
                .map(LigneVentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id,Integer idEntreprise) {
        if (id == null){
            log.error("L'id is null");
        }
        List<LigneVentes> lig = articleRepository.findByligneVentes_IdAndIdEntreprise(id,idEntreprise);
        if (lig.isEmpty()){
            log.error("la liste est vide");
        }
        lig.forEach(ligneVentes -> {
            ligneVentesRepository.deleteById(ligneVentes.getId());
        });
        articleRepository.deleteById(id);
    }
}
