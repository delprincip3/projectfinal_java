package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.UtenteDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.UtenteMapper;
import com.scuola.gestione_corsi.model.Utente;
import com.scuola.gestione_corsi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteMapper utenteMapper;

    public List<UtenteDTO> findAll() {
        return utenteRepository.findAll()
                .stream()
                .map(utenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UtenteDTO findById(Long id) {
        return utenteRepository.findById(id)
                .map(utenteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));
    }

    public UtenteDTO update(Long id, UtenteDTO dto) {
        if (!utenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utente non trovato con id: " + id);
        }
        dto.setId(id);
        Utente utente = utenteMapper.toEntity(dto);
        Utente updated = utenteRepository.save(utente);
        return utenteMapper.toDTO(updated);
    }
} 