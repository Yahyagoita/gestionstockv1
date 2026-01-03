package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.UtilisateurDto;
import com.gestionStocks.gestionStock.service.UtilisateurService;
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
@RequestMapping(APP_ROOT +"/utilisateurs")
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping(
          value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Créer un utilisateur", description = "Permet de créer un nouvel utilisateur"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Utilisateur créé",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UtilisateurDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Utilisateur invalide"
            )
    })
    public UtilisateurDto save(@RequestBody UtilisateurDto dto) {
        return utilisateurService.save(dto);
    }

    @PutMapping(
            value = "/modifie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier un utilisateur", description = "Permet de modifier un utilisateur existant par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Utilisateur modifié",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UtilisateurDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Utilisateur non trouvé"
            )
    })
    public UtilisateurDto update(
            @PathVariable Integer id, @RequestBody UtilisateurDto dto
    ) {
        return utilisateurService.update(id, dto);
    }

    @GetMapping(value = "/by-entreprise/{idEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister les utilisateurs par entreprise", description = "Retourne les utilisateurs associés à une entreprise"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des utilisateurs"
            )
    })
    public List<UtilisateurDto> findByEntreprise(
            @PathVariable Integer idEntreprise
    ) {
        return utilisateurService.findUtilisateurEntreprise(idEntreprise);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister tous les utilisateurs", description = "Retourne la liste complète des utilisateurs"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des utilisateurs"
            )
    })
    public List<UtilisateurDto> findAll() {
        return utilisateurService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher un utilisateur par ID", description = "Retourne un utilisateur à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Utilisateur trouvé",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UtilisateurDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Utilisateur non trouvé"
            )
    })
    public UtilisateurDto findById(@PathVariable Integer id) {
        return utilisateurService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Supprimer un utilisateur", description = "Supprime un utilisateur par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Utilisateur supprimé"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Utilisateur non trouvé"
            )
    })
    public void delete(@PathVariable Integer id) {
        utilisateurService.delete(id);
    }
}
