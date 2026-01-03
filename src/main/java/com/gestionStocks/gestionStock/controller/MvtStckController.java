package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.MvtStckDto;
import com.gestionStocks.gestionStock.model.TypeMvt;
import com.gestionStocks.gestionStock.service.MvtStckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestionStocks.gestionStock.utils.Constante.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT+"/mvtStock")
@Slf4j
public class MvtStckController {

    private MvtStckService mvtStckService;
    @Autowired
    public MvtStckController(MvtStckService mvtStckService){
        this.mvtStckService = mvtStckService;
    }
    @PostMapping(
            value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "faire un ajoute ou sortie de stock", description = "cette methode permet d'ajouter ou dimunierle Stock"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",description = "Mvt creer ",
                    content = @Content(
                            schema = @Schema(implementation = MvtStckDto.class)
            )),
            @ApiResponse(
                    responseCode = "400", description = "Mvt invalide"
            )
    })
    public MvtStckDto save(@RequestBody MvtStckDto dto){
        return mvtStckService.saveEntreeSORTIE(dto);
    }
    @PutMapping(
            value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "modifier un ajoute ou sortie de stock",description = "cette methode permet modifier Stock"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",description = "Mvt modifier ",
                    content = @Content(
                            schema = @Schema(implementation = MvtStckDto.class)
                    )),
            @ApiResponse(
                    responseCode = "400", description = "Mvt invalide"
            )
    })
    public MvtStckDto update(@PathVariable Integer id, @RequestBody MvtStckDto dto){
        return mvtStckService.updateEntreeSortie(id, dto);
    }
    @GetMapping(
            value = "/ListeENTREouSORTIE/{idEntreprise}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "La liste des ENTREouSORTIEMvts",description = "Methode qui affiche la Liste des ENTREouSORTIEMvts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",description = "Liste des Mvts"
            )
    })
    public List<MvtStckDto> listMvtENTREouSORTIE(@PathVariable Integer idEntreprise, @RequestParam TypeMvt typeMvt){
        log.error("ok");
        return mvtStckService.findAllByEntrepriseByType(idEntreprise, typeMvt);
    }
    @GetMapping(
            value = "/ListeMvt/{idEntreprise}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "La liste des mvt",description = "Methode qui affiche la Liste des Mvts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",description = "Liste des Mvts"
            )
    })
    public List<MvtStckDto> listMvt(@PathVariable Integer idEntreprise){
        log.error("ok pour la liste total");
        return mvtStckService.findAllByEntreprise(idEntreprise);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(
            summary = "Supp un Mvt ", description = "Methode pour supprimer un Mvt"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",description = "Mvt Supprimer ",
                    content = @Content(
                            schema = @Schema(implementation = MvtStckDto.class)
                    )),
            @ApiResponse(
                    responseCode = "400", description = "Mvt invalide"
            )
    })
    public void delete(@PathVariable Integer id){
        mvtStckService.delete(id);
    }

}
