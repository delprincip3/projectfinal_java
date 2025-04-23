package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.ValutazioneDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.ValutazioneMapper;
import com.scuola.gestione_corsi.model.Valutazione;
import com.scuola.gestione_corsi.repository.ValutazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ValutazioneService {

    @Autowired
    private ValutazioneRepository valutazioneRepository;

    @Autowired
    private ValutazioneMapper valutazioneMapper;

    public List<ValutazioneDTO> findAll() {
        return valutazioneRepository.findAll()
                .stream()
                .map(valutazioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ValutazioneDTO findById(Long id) {
        return valutazioneRepository.findById(id)
                .map(valutazioneMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Valutazione non trovata con id: " + id));
    }

    public List<ValutazioneDTO> findByIscrizioneId(Long iscrizioneId) {
        return valutazioneRepository.findByIscrizione_Id(iscrizioneId)
                .stream()
                .map(valutazioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ValutazioneDTO create(ValutazioneDTO dto) {
        Valutazione valutazione = valutazioneMapper.toEntity(dto);
        Valutazione saved = valutazioneRepository.save(valutazione);
        return valutazioneMapper.toDTO(saved);
    }

    public ValutazioneDTO update(Long id, ValutazioneDTO dto) {
        if (!valutazioneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Valutazione non trovata con id: " + id);
        }
        dto.setId(id);
        Valutazione valutazione = valutazioneMapper.toEntity(dto);
        Valutazione updated = valutazioneRepository.save(valutazione);
        return valutazioneMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!valutazioneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Valutazione non trovata con id: " + id);
        }
        valutazioneRepository.deleteById(id);
    }
} 