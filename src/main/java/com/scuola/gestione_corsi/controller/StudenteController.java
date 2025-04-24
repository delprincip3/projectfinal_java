package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.StudenteDTO;
import com.scuola.gestione_corsi.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studenti")
public class StudenteController {

    @Autowired
    private StudenteService studenteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public ResponseEntity<List<StudenteDTO>> findAll() {
        return ResponseEntity.ok(studenteService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'STUDENTE')")
    public ResponseEntity<StudenteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studenteService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudenteDTO> create(@RequestBody StudenteDTO studenteDTO) {
        return ResponseEntity.ok(studenteService.create(studenteDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENTE')")
    public ResponseEntity<StudenteDTO> update(@PathVariable Long id, @RequestBody StudenteDTO studenteDTO) {
        return ResponseEntity.ok(studenteService.update(id, studenteDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studenteService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/corso/{corsoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public ResponseEntity<List<StudenteDTO>> findByCorsoId(@PathVariable Long corsoId) {
        return ResponseEntity.ok(studenteService.findByCorsoId(corsoId));
    }
} 