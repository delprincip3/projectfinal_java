package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.LezioneDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.LezioneMapper;
import com.scuola.gestione_corsi.model.Lezione;
import com.scuola.gestione_corsi.repository.LezioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LezioneService {

    @Autowired
    private LezioneRepository lezioneRepository;

    @Autowired
    private LezioneMapper lezioneMapper;

    public List<LezioneDTO> findAll() {
        return lezioneRepository.findAll()
                .stream()
                .map(lezioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LezioneDTO findById(Long id) {
        return lezioneRepository.findById(id)
                .map(lezioneMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Lezione non trovata con id: " + id));
    }

    public List<LezioneDTO> findByCorsoId(Long corsoId) {
        return lezioneRepository.findByCorsoId(corsoId)
                .stream()
                .map(lezioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LezioneDTO> findByDocenteId(Long docenteId) {
        return lezioneRepository.findByDocenteId(docenteId)
                .stream()
                .map(lezioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LezioneDTO> findByAulaId(Long aulaId) {
        return lezioneRepository.findByAulaId(aulaId)
                .stream()
                .map(lezioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LezioneDTO create(LezioneDTO dto) {
        Lezione lezione = lezioneMapper.toEntity(dto);
        Lezione saved = lezioneRepository.save(lezione);
        return lezioneMapper.toDTO(saved);
    }

    public LezioneDTO update(Long id, LezioneDTO dto) {
        if (!lezioneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lezione non trovata con id: " + id);
        }
        dto.setId(id);
        Lezione lezione = lezioneMapper.toEntity(dto);
        Lezione updated = lezioneRepository.save(lezione);
        return lezioneMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!lezioneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lezione non trovata con id: " + id);
        }
        lezioneRepository.deleteById(id);
    }
} 