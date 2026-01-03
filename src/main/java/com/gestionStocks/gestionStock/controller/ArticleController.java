package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.ArticleDto;
import com.gestionStocks.gestionStock.dto.LigneVentesDto;
import com.gestionStocks.gestionStock.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestionStocks.gestionStock.utils.Constante.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT + "/articles")
@Tag(name = "Articles", description = "Gestion des articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(
            value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Créer un article",description = "creation d'un article"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article créé",
                    content = @Content(schema = @Schema(implementation = ArticleDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Article invalide"
            )
    })
    public ArticleDto save(@RequestBody ArticleDto dto) {
        return articleService.save(dto);
    }
    @PutMapping(
            value = "/modifie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier un article",description = "Cette methode permet de modifire un article"

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Article modifié",
                    content = @Content(schema = @Schema(implementation = ArticleDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Article non valide"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Catégorie non trouvée"
            )
    })
    public ArticleDto update(
            @PathVariable Integer id, @RequestBody ArticleDto dto
    ) {
        return articleService.update(id, dto);
    }
    @GetMapping(
            value = "/find-by-nom", produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Rechercher un article par code",description = "Cette methode permet de faire la rechercher par le nom"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Article trouvé",
                    content = @Content(schema = @Schema(implementation = ArticleDto.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Article non trouvé"
            )
    })
    public ArticleDto findByNom(@RequestParam String nom, @PathVariable Integer idEntre) {
        return articleService.findByNomAndIdEntreprise(nom, idEntre);
    }
    @GetMapping(
            value = "/find-by-code", produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Rechercher un article par code",description = "Cette methode permet de faire la rechercher par le code"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Article trouvé",
                    content = @Content(schema = @Schema(implementation = ArticleDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "Article non trouvé"
            )
    })
    public ArticleDto findByCode(@RequestParam String codeArticle, @PathVariable Integer idEntre) {
        return articleService.findByCodeArticleAndIdEntreprise(codeArticle, idEntre);
    }
    @GetMapping(value = "/by-category", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister les articles par catégorie et entreprise",description = "Cette methode permet d'afficher la liste des articles par category"

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des article par category"
            )
    })
    public List<ArticleDto> findByCategorieAndEntreprise(
            @RequestParam Integer idCategorie, @RequestParam Integer idEntreprise
    ) {
        return articleService.findAllArticleByIdCategory(idCategorie, idEntreprise);
    }
    @GetMapping(
            value = "/liste-articles", produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Lister les articles d'une entreprise",description = "Cette methode permet d'afficher la liste des articles"

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des articles"
            )
    })
    public List<ArticleDto> findAllByEntreprise(@RequestParam Integer idEntreprise) {
        return articleService.findAllEntre(idEntreprise);
    }
    @GetMapping(value = "/historique-ventes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Historique des ventes d'un article par entreprise",description = "Cette methode permet d'afficher l'historique par article "

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "L'historique des Ventes par article"
            )
    })

    public List<LigneVentesDto> findHistoriqueVentes(
            @RequestParam Integer idArticle, @RequestParam Integer idEntreprise
    ) {
        return articleService.findHistoriqueVentes(idArticle, idEntreprise);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Supprimer un article",description = "Cette methode permet de supprimer un article")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Article supprimé"
            )
    })
    public void delete(@PathVariable Integer id,@RequestParam Integer idEntreprise) {
        articleService.delete(id,idEntreprise);
    }
}
