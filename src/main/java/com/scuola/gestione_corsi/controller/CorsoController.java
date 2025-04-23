package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.CorsoDTO;
import com.scuola.gestione_corsi.service.CorsoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corsi")
@Tag(name = "Corso", description = "API per la gestione dei corsi")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;

    @GetMapping
    @Operation(summary = "Ottiene tutti i corsi")
    public ResponseEntity<List<CorsoDTO>> findAll() {
        return ResponseEntity.ok(corsoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene un corso per ID")
    public ResponseEntity<CorsoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(corsoService.findById(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Ottiene i corsi di una categoria")
    public ResponseEntity<List<CorsoDTO>> findByCategoriaId(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(corsoService.findByCategoriaId(categoriaId));
    }

    @GetMapping("/docente/{docenteId}")
    @Operation(summary = "Ottiene i corsi di un docente")
    public ResponseEntity<List<CorsoDTO>> findByDocenteId(@PathVariable Long docenteId) {
        return ResponseEntity.ok(corsoService.findByDocenteId(docenteId));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo corso")
    public ResponseEntity<CorsoDTO> create(@RequestBody CorsoDTO dto) {
        return ResponseEntity.ok(corsoService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna un corso esistente")
    public ResponseEntity<CorsoDTO> update(@PathVariable Long id, @RequestBody CorsoDTO dto) {
        return ResponseEntity.ok(corsoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un corso")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        corsoService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 