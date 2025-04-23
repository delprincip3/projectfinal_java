package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.DocenteDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.DocenteMapper;
import com.scuola.gestione_corsi.model.Docente;
import com.scuola.gestione_corsi.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteMapper docenteMapper;

    public List<DocenteDTO> findAll() {
        return docenteRepository.findAll()
                .stream()
                .map(docenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DocenteDTO findById(Long id) {
        return docenteRepository.findById(id)
                .map(docenteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Docente non trovato con id: " + id));
    }

    public List<DocenteDTO> findBySpecializzazione(String specializzazione) {
        return docenteRepository.findBySpecializzazione(specializzazione)
                .stream()
                .map(docenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DocenteDTO create(DocenteDTO dto) {
        Docente docente = docenteMapper.toEntity(dto);
        Docente saved = docenteRepository.save(docente);
        return docenteMapper.toDTO(saved);
    }

    public DocenteDTO update(Long id, DocenteDTO dto) {
        if (!docenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Docente non trovato con id: " + id);
        }
        dto.setId(id);
        Docente docente = docenteMapper.toEntity(dto);
        Docente updated = docenteRepository.save(docente);
        return docenteMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!docenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Docente non trovato con id: " + id);
        }
        docenteRepository.deleteById(id);
    }
} 