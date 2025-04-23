package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.ValutazioneDTO;
import com.scuola.gestione_corsi.service.ValutazioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valutazioni")
@Tag(name = "Valutazione", description = "API per la gestione delle valutazioni")
public class ValutazioneController {

    @Autowired
    private ValutazioneService valutazioneService;

    @GetMapping
    @Operation(summary = "Ottiene tutte le valutazioni")
    public ResponseEntity<List<ValutazioneDTO>> findAll() {
        return ResponseEntity.ok(valutazioneService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene una valutazione per ID")
    public ResponseEntity<ValutazioneDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(valutazioneService.findById(id));
    }

    @GetMapping("/iscrizione/{iscrizioneId}")
    @Operation(summary = "Ottiene le valutazioni di un'iscrizione")
    public ResponseEntity<List<ValutazioneDTO>> findByIscrizioneId(@PathVariable Long iscrizioneId) {
        return ResponseEntity.ok(valutazioneService.findByIscrizioneId(iscrizioneId));
    }

    @PostMapping
    @Operation(summary = "Crea una nuova valutazione")
    public ResponseEntity<ValutazioneDTO> create(@RequestBody ValutazioneDTO dto) {
        return ResponseEntity.ok(valutazioneService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna una valutazione esistente")
    public ResponseEntity<ValutazioneDTO> update(@PathVariable Long id, @RequestBody ValutazioneDTO dto) {
        return ResponseEntity.ok(valutazioneService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una valutazione")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        valutazioneService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 