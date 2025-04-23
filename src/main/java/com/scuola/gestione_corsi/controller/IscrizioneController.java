package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.IscrizioneDTO;
import com.scuola.gestione_corsi.model.StatoIscrizione;
import com.scuola.gestione_corsi.service.IscrizioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iscrizioni")
@Tag(name = "Iscrizione", description = "API per la gestione delle iscrizioni")
public class IscrizioneController {

    @Autowired
    private IscrizioneService iscrizioneService;

    @GetMapping
    @Operation(summary = "Ottiene tutte le iscrizioni")
    public ResponseEntity<List<IscrizioneDTO>> findAll() {
        return ResponseEntity.ok(iscrizioneService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene un'iscrizione per ID")
    public ResponseEntity<IscrizioneDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(iscrizioneService.findById(id));
    }

    @GetMapping("/studente/{studenteId}")
    @Operation(summary = "Ottiene le iscrizioni di uno studente")
    public ResponseEntity<List<IscrizioneDTO>> findByStudenteId(@PathVariable Long studenteId) {
        return ResponseEntity.ok(iscrizioneService.findByStudenteId(studenteId));
    }

    @GetMapping("/corso/{corsoId}")
    @Operation(summary = "Ottiene le iscrizioni a un corso")
    public ResponseEntity<List<IscrizioneDTO>> findByCorsoId(@PathVariable Long corsoId) {
        return ResponseEntity.ok(iscrizioneService.findByCorsoId(corsoId));
    }

    @GetMapping("/stato/{stato}")
    @Operation(summary = "Ottiene le iscrizioni per stato")
    public ResponseEntity<List<IscrizioneDTO>> findByStato(@PathVariable String stato) {
        return ResponseEntity.ok(iscrizioneService.findByStato(StatoIscrizione.valueOf(stato.toUpperCase())));
    }

    @PostMapping
    @Operation(summary = "Crea una nuova iscrizione")
    public ResponseEntity<IscrizioneDTO> create(@RequestBody IscrizioneDTO dto) {
        return ResponseEntity.ok(iscrizioneService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna un'iscrizione esistente")
    public ResponseEntity<IscrizioneDTO> update(@PathVariable Long id, @RequestBody IscrizioneDTO dto) {
        return ResponseEntity.ok(iscrizioneService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un'iscrizione")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iscrizioneService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 