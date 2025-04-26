package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.MaterialeDidatticoDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.MaterialeDidatticoMapper;
import com.scuola.gestione_corsi.model.MaterialeDidattico;
import com.scuola.gestione_corsi.repository.MaterialeDidatticoRepository;
import com.scuola.gestione_corsi.repository.LezioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MaterialeDidatticoService {

    @Autowired
    private MaterialeDidatticoRepository materialeDidatticoRepository;

    @Autowired
    private MaterialeDidatticoMapper materialeDidatticoMapper;

    @Autowired
    private LezioneRepository lezioneRepository;

    @Transactional(readOnly = true)
    public List<MaterialeDidatticoDTO> findAll() {
        return materialeDidatticoRepository.findAll()
                .stream()
                .map(materialeDidatticoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MaterialeDidatticoDTO findById(Long id) {
        return materialeDidatticoRepository.findById(id)
                .map(materialeDidatticoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Materiale didattico non trovato con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<MaterialeDidatticoDTO> findByLezioneId(Long lezioneId) {
        if (lezioneId == null) {
            throw new IllegalArgumentException("L'ID della lezione è obbligatorio per la ricerca");
        }
        
        if (!lezioneRepository.existsById(lezioneId)) {
            throw new ResourceNotFoundException("Lezione non trovata con id: " + lezioneId);
        }
        
        return materialeDidatticoRepository.findByLezione_Id(lezioneId)
                .stream()
                .map(materialeDidatticoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MaterialeDidatticoDTO create(MaterialeDidatticoDTO dto) {
        validateLezioneId(dto.getLezioneId());
        
        MaterialeDidattico materiale = materialeDidatticoMapper.toEntity(dto);
        MaterialeDidattico saved = materialeDidatticoRepository.save(materiale);
        return materialeDidatticoMapper.toDTO(saved);
    }

    @Transactional
    public MaterialeDidatticoDTO update(Long id, MaterialeDidatticoDTO dto) {
        if (!materialeDidatticoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materiale didattico non trovato con id: " + id);
        }
        
        validateLezioneId(dto.getLezioneId());
        
        dto.setId(id);
        MaterialeDidattico materiale = materialeDidatticoMapper.toEntity(dto);
        MaterialeDidattico updated = materialeDidatticoRepository.save(materiale);
        return materialeDidatticoMapper.toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!materialeDidatticoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materiale didattico non trovato con id: " + id);
        }
        materialeDidatticoRepository.deleteById(id);
    }

    private void validateLezioneId(Long lezioneId) {
        if (lezioneId == null) {
            throw new IllegalArgumentException("L'ID della lezione è obbligatorio");
        }
        
        if (!lezioneRepository.existsById(lezioneId)) {
            throw new ResourceNotFoundException("Lezione non trovata con id: " + lezioneId);
        }
    }
} 