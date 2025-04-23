package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.DocenteDTO;
import com.scuola.gestione_corsi.service.DocenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docenti")
@Tag(name = "Docente", description = "API per la gestione dei docenti")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @GetMapping
    @Operation(summary = "Ottieni tutti i docenti")
    public ResponseEntity<List<DocenteDTO>> findAll() {
        return ResponseEntity.ok(docenteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottieni un docente per ID")
    public ResponseEntity<DocenteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(docenteService.findById(id));
    }

    @GetMapping("/specializzazione/{specializzazione}")
    @Operation(summary = "Ottieni docenti per specializzazione")
    public ResponseEntity<List<DocenteDTO>> findBySpecializzazione(@PathVariable String specializzazione) {
        return ResponseEntity.ok(docenteService.findBySpecializzazione(specializzazione));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo docente")
    public ResponseEntity<DocenteDTO> create(@RequestBody DocenteDTO dto) {
        return ResponseEntity.ok(docenteService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna un docente esistente")
    public ResponseEntity<DocenteDTO> update(@PathVariable Long id, @RequestBody DocenteDTO dto) {
        return ResponseEntity.ok(docenteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un docente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docenteService.delete(id);
        return ResponseEntity.ok().build();
    }
} 