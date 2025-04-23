package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.PresenzaDTO;
import com.scuola.gestione_corsi.service.PresenzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presenze")
@Tag(name = "Presenza", description = "API per la gestione delle presenze")
public class PresenzaController {

    @Autowired
    private PresenzaService presenzaService;

    @GetMapping
    @Operation(summary = "Ottiene tutte le presenze")
    public ResponseEntity<List<PresenzaDTO>> findAll() {
        return ResponseEntity.ok(presenzaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene una presenza per ID")
    public ResponseEntity<PresenzaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(presenzaService.findById(id));
    }

    @GetMapping("/iscrizione/{iscrizioneId}")
    @Operation(summary = "Ottiene le presenze di un'iscrizione")
    public ResponseEntity<List<PresenzaDTO>> findByIscrizioneId(@PathVariable Long iscrizioneId) {
        return ResponseEntity.ok(presenzaService.findByIscrizioneId(iscrizioneId));
    }

    @PostMapping
    @Operation(summary = "Crea una nuova presenza")
    public ResponseEntity<PresenzaDTO> create(@RequestBody PresenzaDTO dto) {
        return ResponseEntity.ok(presenzaService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna una presenza esistente")
    public ResponseEntity<PresenzaDTO> update(@PathVariable Long id, @RequestBody PresenzaDTO dto) {
        return ResponseEntity.ok(presenzaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una presenza")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        presenzaService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 