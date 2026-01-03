package com.gestionStocks.gestionStock.controller;

import com.gestionStocks.gestionStock.dto.ClientDto;
import com.gestionStocks.gestionStock.dto.DetteDto;
import com.gestionStocks.gestionStock.service.ClientService;
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
@RequestMapping(APP_ROOT+"/client")
public class ClientController {
    private final ClientService clientService;
    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    @PostMapping(
            value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Creer un Client",description = "creation de client"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "creer avec succes",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Client invalide"
            )
    })
    public ClientDto save(@RequestBody ClientDto dto){
        return clientService.save(dto);
    }
    @PutMapping(
            value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Modifier un client", description = "Modification d'un client"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Client modifiee ",
                    content = @Content(schema = @Schema(implementation = DetteDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",description = "client introuvable"
            )
    })
    public ClientDto update(@PathVariable Integer id, @RequestBody ClientDto dto){
        return clientService.update(id, dto);
    }
    @GetMapping(value = "/findAllClient", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Liste des Clients de entreprise",description = "Cette methode permet d'afficher la liste des clients "

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Liste des clients"
            )
    })
    public List<ClientDto> findAllclient(@PathVariable Integer id){
        return clientService.findByEntre(id);
    }
    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Supprimer un client",description = "Cette methode permet de supprimer un client")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "client supprimé"
            )
    })
    void delete(@PathVariable Integer id){
          clientService.delete(id);
    }
}
