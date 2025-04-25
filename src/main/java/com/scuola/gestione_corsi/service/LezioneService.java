package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.LezioneDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.LezioneMapper;
import com.scuola.gestione_corsi.model.Lezione;
import com.scuola.gestione_corsi.repository.LezioneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LezioneService {

    private final LezioneRepository lezioneRepository;
    private final LezioneMapper lezioneMapper;

    @Transactional(readOnly = true)
    public List<LezioneDTO> findAll() {
        log.debug("Richiesta elenco lezioni");
        return lezioneRepository.findAll().stream()
            .map(lezioneMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LezioneDTO findById(Long id) {
        log.debug("Richiesta lezione con ID: {}", id);
        return lezioneRepository.findById(id)
            .map(lezioneMapper::toDTO)
            .orElseThrow(() -> new RuntimeException("Lezione non trovata con ID: " + id));
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

    @Transactional
    public LezioneDTO create(LezioneDTO dto) {
        log.debug("Richiesta creazione nuova lezione: {}", dto);
        
        // Validazione dei dati
        validateLezione(dto);
        
        // Verifica sovrapposizione oraria
        checkSovrapposizioneOraria(dto);
        
        Lezione lezione = lezioneMapper.toEntity(dto);
        Lezione saved = lezioneRepository.save(lezione);
        log.info("Lezione creata con successo: {}", saved);
        
        return lezioneMapper.toDTO(saved);
    }

    @Transactional
    public LezioneDTO update(Long id, LezioneDTO dto) {
        log.debug("Richiesta aggiornamento lezione con ID {}: {}", id, dto);
        
        if (!lezioneRepository.existsById(id)) {
            throw new RuntimeException("Lezione non trovata con ID: " + id);
        }
        
        // Validazione dei dati
        validateLezione(dto);
        
        // Verifica sovrapposizione oraria (escludendo la lezione corrente)
        checkSovrapposizioneOraria(dto, id);
        
        dto.setId(id);
        Lezione lezione = lezioneMapper.toEntity(dto);
        Lezione updated = lezioneRepository.save(lezione);
        log.info("Lezione aggiornata con successo: {}", updated);
        
        return lezioneMapper.toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Richiesta eliminazione lezione con ID: {}", id);
        if (!lezioneRepository.existsById(id)) {
            throw new RuntimeException("Lezione non trovata con ID: " + id);
        }
        lezioneRepository.deleteById(id);
        log.info("Lezione eliminata con successo: {}", id);
    }

    private void validateLezione(LezioneDTO dto) {
        if (dto.getTitolo() == null || dto.getTitolo().trim().isEmpty()) {
            throw new RuntimeException("Il titolo della lezione è obbligatorio");
        }
        
        if (dto.getDataOra() == null) {
            throw new RuntimeException("La data e ora della lezione sono obbligatorie");
        }
        
        if (dto.getDurata() == null || dto.getDurata() <= 0) {
            throw new RuntimeException("La durata della lezione deve essere maggiore di 0");
        }
        
        if (dto.getCorsoId() == null) {
            throw new RuntimeException("Il corso è obbligatorio");
        }
        
        if (dto.getDocenteId() == null) {
            throw new RuntimeException("Il docente è obbligatorio");
        }
        
        if (dto.getAulaId() == null) {
            throw new RuntimeException("L'aula è obbligatoria");
        }
    }

    private void checkSovrapposizioneOraria(LezioneDTO dto) {
        checkSovrapposizioneOraria(dto, null);
    }

    private void checkSovrapposizioneOraria(LezioneDTO dto, Long excludeId) {
        LocalDateTime inizio = dto.getDataOra();
        LocalDateTime fine = inizio.plusMinutes(dto.getDurata());
        
        // Verifica sovrapposizione per aula
        boolean aulaOccupata = lezioneRepository.existsByAulaIdAndDataOraBetween(
            dto.getAulaId(),
            inizio,
            fine,
            excludeId
        );
        
        if (aulaOccupata) {
            throw new RuntimeException("L'aula è già occupata in questo orario");
        }
        
        // Verifica sovrapposizione per docente
        boolean docenteOccupato = lezioneRepository.existsByDocenteIdAndDataOraBetween(
            dto.getDocenteId(),
            inizio,
            fine,
            excludeId
        );
        
        if (docenteOccupato) {
            throw new RuntimeException("Il docente ha già un'altra lezione in questo orario");
        }
    }
} 