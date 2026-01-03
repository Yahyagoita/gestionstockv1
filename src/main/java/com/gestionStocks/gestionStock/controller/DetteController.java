package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.DetteDto;
import com.gestionStocks.gestionStock.service.DetteService;
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
@RequestMapping(APP_ROOT +"Dette")
public class DetteController {
    private final DetteService detteService;
    @Autowired
    public DetteController(DetteService detteService){
        this.detteService = detteService;
    }
    @PostMapping(
            value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Creer une Dette",description = "creation de dette"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "creer avec succes",
                    content = @Content(schema = @Schema(implementation = DetteDto.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Dette invalide"
            )
    })
    public DetteDto save(@RequestBody DetteDto dto){
        return detteService.recouvrement(dto);
    }

    @PutMapping(
            value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier une dette", description = "Modification d'une dette"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "dette modifiee ",
                    content = @Content(schema = @Schema(implementation = DetteDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",description = "dette introuvable"
            )
    })
    public DetteDto update(@PathVariable Integer id, @RequestBody DetteDto dto){
        return detteService.updaterecouvrement(id, dto);
    }
    @GetMapping(
            value = "/findByclient/{idcl}/{idEntre}",produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Rechercher la dette d'un client" ,description = "faire la recherche d'un dette"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "dette trouver",
                    content = @Content(schema = @Schema(implementation = DetteDto.class))
            ),
            @ApiResponse(responseCode = "404",description = "dette invalid")
    })
    public DetteDto findBydetteclient(@PathVariable Integer idcl,@PathVariable Integer idEntre){
        return detteService.findByIdclientAndIdEntreprise(idcl, idEntre);
    }
    @GetMapping(
            value = "/findByAllclient/{idEntre}",produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Rechercher de tous dettes " ,description = "faire la recherche de tous les dettes"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "dettes trouver",
                    content = @Content(schema = @Schema(implementation = DetteDto.class))
            ),
            @ApiResponse(responseCode = "404",description = "dettes invalid")
    })
    public List<DetteDto> findByAlldetteclient(@PathVariable Integer idEntre){
        return detteService.findAllEntreprise(idEntre);
    }
    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Supprimer une dette",description = "Cette methode permet de supprimer une dette")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Dette supprimé"
            )
    })
    void delete(@PathVariable Integer id){
        detteService.delete(id);
    }
}
