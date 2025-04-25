package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.LezioneDTO;
import com.scuola.gestione_corsi.model.Lezione;
import com.scuola.gestione_corsi.repository.AulaRepository;
import com.scuola.gestione_corsi.repository.CorsoRepository;
import com.scuola.gestione_corsi.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Lezione.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
@RequiredArgsConstructor
public class LezioneMapper {
    
    private final CorsoRepository corsoRepository;
    private final DocenteRepository docenteRepository;
    private final AulaRepository aulaRepository;

    /**
     * Converte un'entità Lezione in un DTO.
     * @param lezione L'entità da convertire
     * @return Il DTO convertito
     */
    public LezioneDTO toDTO(Lezione lezione) {
        if (lezione == null) {
            return null;
        }
        
        LezioneDTO dto = new LezioneDTO();
        dto.setId(lezione.getId());
        dto.setTitolo(lezione.getTitolo());
        dto.setDescrizione(lezione.getDescrizione());
        dto.setDataOra(lezione.getDataOra());
        dto.setDurata(lezione.getDurata());
        
        // Imposta gli ID delle relazioni
        if (lezione.getCorso() != null) {
            dto.setCorsoId(lezione.getCorso().getId());
        }
        
        if (lezione.getDocente() != null) {
            dto.setDocenteId(lezione.getDocente().getId());
        }
        
        if (lezione.getAula() != null) {
            dto.setAulaId(lezione.getAula().getId());
        }
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Lezione.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Lezione toEntity(LezioneDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Lezione lezione = new Lezione();
        lezione.setId(dto.getId());
        lezione.setTitolo(dto.getTitolo());
        lezione.setDescrizione(dto.getDescrizione());
        lezione.setDataOra(dto.getDataOra());
        lezione.setDurata(dto.getDurata());
        
        // Imposta le relazioni
        if (dto.getCorsoId() != null) {
            corsoRepository.findById(dto.getCorsoId())
                .ifPresent(lezione::setCorso);
        }
        
        if (dto.getDocenteId() != null) {
            docenteRepository.findById(dto.getDocenteId())
                .ifPresent(lezione::setDocente);
        }
        
        if (dto.getAulaId() != null) {
            aulaRepository.findById(dto.getAulaId())
                .ifPresent(lezione::setAula);
        }
        
        return lezione;
    }
} 