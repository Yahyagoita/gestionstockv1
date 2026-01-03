package com.gestionStocks.gestionStock.controller;


import com.gestionStocks.gestionStock.dto.CategorieDto;
import com.gestionStocks.gestionStock.service.CategorieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestionStocks.gestionStock.utils.Constante.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT +"/categories")
public class CategorieController {

    private final CategorieService categorieService;

    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }
    @PostMapping(
            value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Créer une catégorie", description = "Permet d'enregistrer une nouvelle catégorie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Catégorie créée",
                    content = @Content(
                            schema = @Schema(implementation = CategorieDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Catégorie invalide"
            )
    })
    public CategorieDto save(@RequestBody CategorieDto dto) {
        return categorieService.save(dto);
    }
    @PutMapping(
            value = "/modifie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier une catégorie", description = "Permet de modifier une catégorie existante par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Catégorie modifiée",
                    content = @Content(
                            schema = @Schema(implementation = CategorieDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Catégorie non valide"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Catégorie non trouvée"
            )
    })
    public CategorieDto update(
            @PathVariable Integer id, @RequestBody CategorieDto dto
    ) {
        return categorieService.update(id, dto);
    }
    @GetMapping(
            value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister toutes les catégories", description = "Retourne la liste de toutes les catégories"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des catégories"
            )
    })
    public List<CategorieDto> findAll() {
        return categorieService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher une catégorie par ID", description = "Retourne une catégorie à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Catégorie trouvée",
                    content = @Content(
                            schema = @Schema(implementation = CategorieDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Catégorie non trouvée"
            )
    })
    public CategorieDto findById(@PathVariable Integer id) {
        return categorieService.findById(id);
    }
    @GetMapping(value = "/by-entrepriseCategoryListe/{idEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister toutes les catégories", description = "Retourne la liste de toutes les catégories"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des catégories"
            )
    })
    public List<CategorieDto> findByIdEntreprise(
            @PathVariable Integer idEntreprise
    ) {
        return categorieService.findByIdEntreprise(idEntreprise);
    }
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable Integer id) {
        categorieService.delete(id);
    }
}
