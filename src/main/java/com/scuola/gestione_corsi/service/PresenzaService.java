package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.PresenzaDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.PresenzaMapper;
import com.scuola.gestione_corsi.model.Presenza;
import com.scuola.gestione_corsi.repository.PresenzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PresenzaService {

    @Autowired
    private PresenzaRepository presenzaRepository;

    @Autowired
    private PresenzaMapper presenzaMapper;

    public List<PresenzaDTO> findAll() {
        return presenzaRepository.findAll()
                .stream()
                .map(presenzaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PresenzaDTO findById(Long id) {
        return presenzaRepository.findById(id)
                .map(presenzaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Presenza non trovata con id: " + id));
    }

    public List<PresenzaDTO> findByIscrizioneId(Long iscrizioneId) {
        return presenzaRepository.findByIscrizione_Id(iscrizioneId)
                .stream()
                .map(presenzaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PresenzaDTO create(PresenzaDTO dto) {
        Presenza presenza = presenzaMapper.toEntity(dto);
        Presenza saved = presenzaRepository.save(presenza);
        return presenzaMapper.toDTO(saved);
    }

    public PresenzaDTO update(Long id, PresenzaDTO dto) {
        if (!presenzaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Presenza non trovata con id: " + id);
        }
        dto.setId(id);
        Presenza presenza = presenzaMapper.toEntity(dto);
        Presenza updated = presenzaRepository.save(presenza);
        return presenzaMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!presenzaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Presenza non trovata con id: " + id);
        }
        presenzaRepository.deleteById(id);
    }
} 