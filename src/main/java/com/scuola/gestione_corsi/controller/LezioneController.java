package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.LezioneDTO;
import com.scuola.gestione_corsi.service.LezioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lezioni")
@Tag(name = "Lezione", description = "API per la gestione delle lezioni")
public class LezioneController {

    @Autowired
    private LezioneService lezioneService;

    @GetMapping
    @Operation(summary = "Ottiene tutte le lezioni")
    public ResponseEntity<List<LezioneDTO>> findAll() {
        return ResponseEntity.ok(lezioneService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene una lezione per ID")
    public ResponseEntity<LezioneDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(lezioneService.findById(id));
    }

    @GetMapping("/corso/{corsoId}")
    @Operation(summary = "Ottiene le lezioni di un corso")
    public ResponseEntity<List<LezioneDTO>> findByCorsoId(@PathVariable Long corsoId) {
        return ResponseEntity.ok(lezioneService.findByCorsoId(corsoId));
    }

    @GetMapping("/docente/{docenteId}")
    @Operation(summary = "Ottiene le lezioni di un docente")
    public ResponseEntity<List<LezioneDTO>> findByDocenteId(@PathVariable Long docenteId) {
        return ResponseEntity.ok(lezioneService.findByDocenteId(docenteId));
    }

    @GetMapping("/aula/{aulaId}")
    @Operation(summary = "Ottiene le lezioni in un'aula")
    public ResponseEntity<List<LezioneDTO>> findByAulaId(@PathVariable Long aulaId) {
        return ResponseEntity.ok(lezioneService.findByAulaId(aulaId));
    }

    @PostMapping
    @Operation(summary = "Crea una nuova lezione")
    public ResponseEntity<LezioneDTO> create(@RequestBody LezioneDTO dto) {
        return ResponseEntity.ok(lezioneService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna una lezione esistente")
    public ResponseEntity<LezioneDTO> update(@PathVariable Long id, @RequestBody LezioneDTO dto) {
        return ResponseEntity.ok(lezioneService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una lezione")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lezioneService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 