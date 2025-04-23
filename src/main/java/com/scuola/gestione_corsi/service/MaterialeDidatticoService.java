package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.MaterialeDidatticoDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.MaterialeDidatticoMapper;
import com.scuola.gestione_corsi.model.MaterialeDidattico;
import com.scuola.gestione_corsi.repository.MaterialeDidatticoRepository;
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

    public List<MaterialeDidatticoDTO> findAll() {
        return materialeDidatticoRepository.findAll()
                .stream()
                .map(materialeDidatticoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MaterialeDidatticoDTO findById(Long id) {
        return materialeDidatticoRepository.findById(id)
                .map(materialeDidatticoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Materiale didattico non trovato con id: " + id));
    }

    public List<MaterialeDidatticoDTO> findByLezioneId(Long lezioneId) {
        return materialeDidatticoRepository.findByLezione_Id(lezioneId)
                .stream()
                .map(materialeDidatticoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MaterialeDidatticoDTO create(MaterialeDidatticoDTO dto) {
        MaterialeDidattico materiale = materialeDidatticoMapper.toEntity(dto);
        MaterialeDidattico saved = materialeDidatticoRepository.save(materiale);
        return materialeDidatticoMapper.toDTO(saved);
    }

    public MaterialeDidatticoDTO update(Long id, MaterialeDidatticoDTO dto) {
        if (!materialeDidatticoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materiale didattico non trovato con id: " + id);
        }
        dto.setId(id);
        MaterialeDidattico materiale = materialeDidatticoMapper.toEntity(dto);
        MaterialeDidattico updated = materialeDidatticoRepository.save(materiale);
        return materialeDidatticoMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!materialeDidatticoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materiale didattico non trovato con id: " + id);
        }
        materialeDidatticoRepository.deleteById(id);
    }
} 