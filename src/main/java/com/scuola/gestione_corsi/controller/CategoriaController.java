package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.CategoriaDTO;
import com.scuola.gestione_corsi.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorie")
@Tag(name = "Categoria", description = "API per la gestione delle categorie")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Ottiene tutte le categorie")
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ottiene una categoria per ID")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea una nuova categoria")
    public ResponseEntity<CategoriaDTO> create(@RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna una categoria esistente")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una categoria")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 