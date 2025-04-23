package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.CategoriaDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.CategoriaMapper;
import com.scuola.gestione_corsi.model.Categoria;
import com.scuola.gestione_corsi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO findById(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria non trovata con id: " + id));
    }

    public CategoriaDTO create(CategoriaDTO dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria saved = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(saved);
    }

    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria non trovata con id: " + id);
        }
        dto.setId(id);
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria updated = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria non trovata con id: " + id);
        }
        categoriaRepository.deleteById(id);
    }
} 