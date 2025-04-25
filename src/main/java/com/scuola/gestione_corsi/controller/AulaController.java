package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.AulaDTO;
import com.scuola.gestione_corsi.model.Aula;
import com.scuola.gestione_corsi.repository.AulaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/aule")
@RequiredArgsConstructor
@Tag(name = "Aula", description = "API per la gestione delle aule")
public class AulaController {

    private final AulaRepository aulaRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    @Operation(summary = "Ottiene tutte le aule")
    public ResponseEntity<List<AulaDTO>> findAll() {
        log.debug("Richiesta elenco aule");
        List<AulaDTO> aule = aulaRepository.findAll().stream()
            .map(a -> new AulaDTO(a.getId(), a.getNome(), a.getCapienza()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(aule);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    @Operation(summary = "Ottiene un'aula per ID")
    public ResponseEntity<AulaDTO> findById(@PathVariable Long id) {
        log.debug("Richiesta aula con ID: {}", id);
        return aulaRepository.findById(id)
            .map(a -> new AulaDTO(a.getId(), a.getNome(), a.getCapienza()))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crea una nuova aula")
    public ResponseEntity<AulaDTO> create(@RequestBody AulaDTO dto) {
        log.debug("Richiesta creazione nuova aula: {}", dto);
        Aula aula = new Aula();
        aula.setNome(dto.getNome());
        aula.setCapienza(dto.getCapienza());
        
        Aula saved = aulaRepository.save(aula);
        log.info("Aula creata con successo: {}", saved);
        return ResponseEntity.ok(new AulaDTO(saved.getId(), saved.getNome(), saved.getCapienza()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Aggiorna un'aula esistente")
    public ResponseEntity<AulaDTO> update(@PathVariable Long id, @RequestBody AulaDTO dto) {
        log.debug("Richiesta aggiornamento aula con ID {}: {}", id, dto);
        return aulaRepository.findById(id)
            .map(a -> {
                a.setNome(dto.getNome());
                a.setCapienza(dto.getCapienza());
                Aula updated = aulaRepository.save(a);
                log.info("Aula aggiornata con successo: {}", updated);
                return new AulaDTO(updated.getId(), updated.getNome(), updated.getCapienza());
            })
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Elimina un'aula")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Richiesta eliminazione aula con ID: {}", id);
        if (!aulaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aulaRepository.deleteById(id);
        log.info("Aula eliminata con successo: {}", id);
        return ResponseEntity.noContent().build();
    }
} 