package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.CorsoDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.CorsoMapper;
import com.scuola.gestione_corsi.model.Corso;
import com.scuola.gestione_corsi.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CorsoService {

    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private CorsoMapper corsoMapper;

    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll()
                .stream()
                .map(corsoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CorsoDTO findById(Long id) {
        return corsoRepository.findById(id)
                .map(corsoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Corso non trovato con id: " + id));
    }

    public List<CorsoDTO> findByCategoriaId(Long categoriaId) {
        return corsoRepository.findByCategoria_Id(categoriaId)
                .stream()
                .map(corsoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CorsoDTO> findByDocenteId(Long docenteId) {
        return corsoRepository.findByDocenti_Id(docenteId)
                .stream()
                .map(corsoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CorsoDTO create(CorsoDTO dto) {
        Corso corso = corsoMapper.toEntity(dto);
        Corso saved = corsoRepository.save(corso);
        return corsoMapper.toDTO(saved);
    }

    public CorsoDTO update(Long id, CorsoDTO dto) {
        if (!corsoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Corso non trovato con id: " + id);
        }
        dto.setId(id);
        Corso corso = corsoMapper.toEntity(dto);
        Corso updated = corsoRepository.save(corso);
        return corsoMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!corsoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Corso non trovato con id: " + id);
        }
        corsoRepository.deleteById(id);
    }
} 