package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.PagamentoDTO;
import com.scuola.gestione_corsi.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamenti")
@Tag(name = "Pagamento", description = "API per la gestione dei pagamenti")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    @Operation(summary = "Ottiene tutti i pagamenti")
    public ResponseEntity<List<PagamentoDTO>> findAll() {
        return ResponseEntity.ok(pagamentoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene un pagamento per ID")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.findById(id));
    }

    @GetMapping("/iscrizione/{iscrizioneId}")
    @Operation(summary = "Ottiene i pagamenti di un'iscrizione")
    public ResponseEntity<List<PagamentoDTO>> findByIscrizioneId(@PathVariable Long iscrizioneId) {
        return ResponseEntity.ok(pagamentoService.findByIscrizioneId(iscrizioneId));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo pagamento")
    public ResponseEntity<PagamentoDTO> create(@RequestBody PagamentoDTO dto) {
        return ResponseEntity.ok(pagamentoService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna un pagamento esistente")
    public ResponseEntity<PagamentoDTO> update(@PathVariable Long id, @RequestBody PagamentoDTO dto) {
        return ResponseEntity.ok(pagamentoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un pagamento")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 