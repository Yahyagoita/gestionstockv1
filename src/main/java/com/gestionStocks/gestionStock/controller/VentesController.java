package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.LigneVentesDto;
import com.gestionStocks.gestionStock.dto.VentesDto;
import com.gestionStocks.gestionStock.service.VenteService;
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
@RequestMapping(APP_ROOT +"/ventes")
public class VentesController {
    private VenteService venteService;

    @Autowired
    public VentesController(VenteService venteService){
        this.venteService = venteService;
    }
    @PostMapping(
        value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Créer une vente", description = "Permet d'enregistrer une nouvelle vente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Vente créée",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VentesDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Vente invalide"
            )
    })
    public VentesDto save(@RequestBody VentesDto dto) {
        return venteService.save(dto);
    }

    @PutMapping(
            value = "/modifie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier une vente", description = "Permet de modifier une vente existante par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Vente modifiée",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VentesDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Vente non trouvée"
            )
    })
    public VentesDto update(
            @PathVariable Integer id, @RequestBody VentesDto dto
    ) {
        return venteService.update(id, dto);
    }

    @GetMapping(value = "/listeVentes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister toutes les ventes", description = "Retourne la liste de toutes les ventes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des ventes"
            )
    })
    public List<VentesDto> findAll() {
        return venteService.findAll();
    }
    @GetMapping(value = "/listeVentesByEntreprise", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Lister toutes les ventes par Entreprise", description = "Retourne la liste de toutes les ventes par Entreprise"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des ventes par Entreprise"
            )
    })
    public List<VentesDto> findByVenetesEntreprise(Integer id){
        return venteService.findAllByEntreprise(id);
    }
    @GetMapping(value = "/historique/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Historique des ventes", description = "Retourne l'historique des lignes de vente pour une vente donnée"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Historique des ventes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LigneVentesDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Vente non trouvée"
            )
    })
    public List<LigneVentesDto> historique(@PathVariable Integer id) {
        return venteService.historique(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Supprimer une vente",
            description = "Supprime une vente par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vente supprimée"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vente non trouvée"
            )
    })
    public void delete(@PathVariable Integer id) {
        venteService.delete(id);
    }
}
