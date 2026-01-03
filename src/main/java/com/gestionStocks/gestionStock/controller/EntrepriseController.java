package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.EntrepriseDto;
import com.gestionStocks.gestionStock.service.EntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.gestionStocks.gestionStock.utils.Constante.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT +"/entreprises")
@Tag(name = "Entreprises", description = "Gestion des entreprises")
public class EntrepriseController {

    private final EntrepriseService entrepriseService;

    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @PostMapping(
            value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Créer une entreprise", description = "Permet d'enregistrer une nouvelle entreprise"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Entreprise créée",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EntrepriseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Entreprise invalide"
            )
    })
    public EntrepriseDto create(@RequestBody EntrepriseDto dto) {
        return entrepriseService.save(dto);
    }

    @PutMapping(
            value = "/modifie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier une entreprise", description = "Permet de modifier une entreprise existante par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Entreprise modifiée",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EntrepriseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Entreprise non valide"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Entreprise non trouvée"
            )
    })
    public EntrepriseDto update(
            @PathVariable Integer id,
            @RequestBody EntrepriseDto dto
    ) {
        return entrepriseService.update(id, dto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher une entreprise par ID", description = "Retourne une entreprise à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Entreprise trouvée",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EntrepriseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Entreprise non trouvée"
            )
    })
    public EntrepriseDto findById(@PathVariable Integer id) {
        return entrepriseService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Supprimer une entreprise", description = "Supprime une entreprise par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Entreprise supprimée"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Entreprise non trouvée"
            )
    })
    public void delete(@PathVariable Integer id) {
        entrepriseService.delete(id);
    }
}
