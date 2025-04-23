package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.IscrizioneDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.IscrizioneMapper;
import com.scuola.gestione_corsi.model.Iscrizione;
import com.scuola.gestione_corsi.model.StatoIscrizione;
import com.scuola.gestione_corsi.repository.IscrizioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IscrizioneService {

    @Autowired
    private IscrizioneRepository iscrizioneRepository;

    @Autowired
    private IscrizioneMapper iscrizioneMapper;

    public List<IscrizioneDTO> findAll() {
        return iscrizioneRepository.findAll()
                .stream()
                .map(iscrizioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public IscrizioneDTO findById(Long id) {
        return iscrizioneRepository.findById(id)
                .map(iscrizioneMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Iscrizione non trovata con id: " + id));
    }

    public List<IscrizioneDTO> findByStudenteId(Long studenteId) {
        return iscrizioneRepository.findByStudente_Id(studenteId)
                .stream()
                .map(iscrizioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<IscrizioneDTO> findByCorsoId(Long corsoId) {
        return iscrizioneRepository.findByCorso_Id(corsoId)
                .stream()
                .map(iscrizioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<IscrizioneDTO> findByStato(StatoIscrizione stato) {
        return iscrizioneRepository.findByStato(stato)
                .stream()
                .map(iscrizioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public IscrizioneDTO create(IscrizioneDTO dto) {
        Iscrizione iscrizione = iscrizioneMapper.toEntity(dto);
        Iscrizione saved = iscrizioneRepository.save(iscrizione);
        return iscrizioneMapper.toDTO(saved);
    }

    public IscrizioneDTO update(Long id, IscrizioneDTO dto) {
        if (!iscrizioneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Iscrizione non trovata con id: " + id);
        }
        dto.setId(id);
        Iscrizione iscrizione = iscrizioneMapper.toEntity(dto);
        Iscrizione updated = iscrizioneRepository.save(iscrizione);
        return iscrizioneMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!iscrizioneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Iscrizione non trovata con id: " + id);
        }
        iscrizioneRepository.deleteById(id);
    }
} 