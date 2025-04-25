package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.UtenteDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UtenteDTO>> findAll() {
        return ResponseEntity.ok(utenteService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UtenteDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(utenteService.findById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UtenteDTO> update(@PathVariable Long id, @RequestBody UtenteDTO dto) {
        try {
            return ResponseEntity.ok(utenteService.update(id, dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 