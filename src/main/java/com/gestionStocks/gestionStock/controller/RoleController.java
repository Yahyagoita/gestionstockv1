package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.RoleDto;
import com.gestionStocks.gestionStock.service.RoleService;
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
@RequestMapping(APP_ROOT +"/roles")
@Tag(name = "Rôles", description = "Gestion des rôles")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping(
            value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Créer un rôle", description = "Permet de créer un nouveau rôle pour une entreprise"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Rôle créé",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RoleDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Rôle invalide"
            )
    })
    public RoleDto create(@RequestBody RoleDto dto) {
        return roleService.save(dto);
    }

    @PutMapping(
            value = "/modifie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier un rôle", description = "Permet de modifier un rôle existant par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Rôle modifié",
                    content = @Content(
                            schema = @Schema(implementation = RoleDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Rôle non trouvé"
            )
    })
    public RoleDto update(
            @PathVariable Integer id, @RequestBody RoleDto dto
    ) {
        return roleService.update(id, dto);
    }
    @GetMapping(
            value = "/liste-roles",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister les rôles par filtre", description = "Retourne les rôles par nom et par entreprise"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des rôles"
            )
    })
    public List<RoleDto> listeRole(
            @RequestParam String nom, @RequestParam Integer idEntreprise
    ) {
        return roleService.findByNomRoleAndIdEntreprise(nom, idEntreprise);
    }
    @GetMapping(
            value = "/liste-rolesByEntreprise",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister les rôles par Entreprise", description = "Retourne la liste des rôles de entreprise"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des rôles"
            )
    })
    public List<RoleDto> listeRoleByEntreprise(
            @RequestParam Integer idEntreprise
    ) {
        return roleService.findAllByEntreprise(idEntreprise);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher un rôle par ID", description = "Retourne un rôle à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Rôle trouvé",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Rôle non trouvé"
            )
    })
    public RoleDto findById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister tous les rôles", description = "Retourne la liste complète des rôles"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des rôles"
            )
    })
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Supprimer un rôle", description = "Supprime un rôle par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Rôle supprimé"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Rôle non trouvé"
            )
    })
    public void delete(@PathVariable Integer id) {
        roleService.delete(id);
    }
}
