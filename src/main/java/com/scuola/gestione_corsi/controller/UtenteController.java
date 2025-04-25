package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.UtenteDTO;
import com.scuola.gestione_corsi.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UtenteDTO>> findAll() {
        return ResponseEntity.ok(utenteService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtenteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtenteDTO> update(@PathVariable Long id, @RequestBody UtenteDTO dto) {
        return ResponseEntity.ok(utenteService.update(id, dto));
    }
} 