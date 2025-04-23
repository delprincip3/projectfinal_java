package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.MaterialeDidatticoDTO;
import com.scuola.gestione_corsi.service.MaterialeDidatticoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiali-didattici")
@Tag(name = "Materiale Didattico", description = "API per la gestione dei materiali didattici")
public class MaterialeDidatticoController {

    @Autowired
    private MaterialeDidatticoService materialeDidatticoService;

    @GetMapping
    @Operation(summary = "Ottiene tutti i materiali didattici")
    public ResponseEntity<List<MaterialeDidatticoDTO>> findAll() {
        return ResponseEntity.ok(materialeDidatticoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene un materiale didattico per ID")
    public ResponseEntity<MaterialeDidatticoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(materialeDidatticoService.findById(id));
    }

    @GetMapping("/lezione/{lezioneId}")
    @Operation(summary = "Ottiene i materiali didattici di una lezione")
    public ResponseEntity<List<MaterialeDidatticoDTO>> findByLezioneId(@PathVariable Long lezioneId) {
        return ResponseEntity.ok(materialeDidatticoService.findByLezioneId(lezioneId));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo materiale didattico")
    public ResponseEntity<MaterialeDidatticoDTO> create(@RequestBody MaterialeDidatticoDTO dto) {
        return ResponseEntity.ok(materialeDidatticoService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna un materiale didattico esistente")
    public ResponseEntity<MaterialeDidatticoDTO> update(@PathVariable Long id, @RequestBody MaterialeDidatticoDTO dto) {
        return ResponseEntity.ok(materialeDidatticoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un materiale didattico")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materialeDidatticoService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 