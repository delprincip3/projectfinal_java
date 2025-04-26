package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.MaterialeDidatticoDTO;
import com.scuola.gestione_corsi.service.MaterialeDidatticoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materiali-didattici")
@Tag(name = "Materiale Didattico", description = "API per la gestione dei materiali didattici")
public class MaterialeDidatticoController {

    @Autowired
    private MaterialeDidatticoService materialeDidatticoService;

    @GetMapping
    @Operation(summary = "Ottiene tutti i materiali didattici")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista dei materiali didattici recuperata con successo"),
        @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<MaterialeDidatticoDTO>> findAll() {
        return ResponseEntity.ok(materialeDidatticoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene un materiale didattico per ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Materiale didattico recuperato con successo"),
        @ApiResponse(responseCode = "404", description = "Materiale didattico non trovato"),
        @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<MaterialeDidatticoDTO> findById(
            @Parameter(description = "ID del materiale didattico", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(materialeDidatticoService.findById(id));
    }

    @GetMapping("/lezione/{lezioneId}")
    @Operation(summary = "Ottiene i materiali didattici di una lezione")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista dei materiali didattici della lezione recuperata con successo"),
        @ApiResponse(responseCode = "404", description = "Lezione non trovata"),
        @ApiResponse(responseCode = "400", description = "ID della lezione non valido"),
        @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<MaterialeDidatticoDTO>> findByLezioneId(
            @Parameter(description = "ID della lezione", required = true)
            @PathVariable Long lezioneId) {
        return ResponseEntity.ok(materialeDidatticoService.findByLezioneId(lezioneId));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo materiale didattico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Materiale didattico creato con successo"),
        @ApiResponse(responseCode = "400", description = "Dati del materiale didattico non validi"),
        @ApiResponse(responseCode = "404", description = "Lezione non trovata"),
        @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<MaterialeDidatticoDTO> create(
            @Parameter(description = "Dati del materiale didattico", required = true)
            @Valid @RequestBody MaterialeDidatticoDTO dto) {
        MaterialeDidatticoDTO created = materialeDidatticoService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna un materiale didattico esistente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Materiale didattico aggiornato con successo"),
        @ApiResponse(responseCode = "400", description = "Dati del materiale didattico non validi"),
        @ApiResponse(responseCode = "404", description = "Materiale didattico o lezione non trovati"),
        @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<MaterialeDidatticoDTO> update(
            @Parameter(description = "ID del materiale didattico", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dati aggiornati del materiale didattico", required = true)
            @Valid @RequestBody MaterialeDidatticoDTO dto) {
        return ResponseEntity.ok(materialeDidatticoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un materiale didattico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Materiale didattico eliminato con successo"),
        @ApiResponse(responseCode = "404", description = "Materiale didattico non trovato"),
        @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del materiale didattico", required = true)
            @PathVariable Long id) {
        materialeDidatticoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
} 