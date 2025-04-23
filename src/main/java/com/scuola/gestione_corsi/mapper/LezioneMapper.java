package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.LezioneDTO;
import com.scuola.gestione_corsi.model.Lezione;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Lezione.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class LezioneMapper {
    
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
        dto.setCorsoId(lezione.getCorso().getId());
        dto.setDocenteId(lezione.getDocente().getId());
        dto.setAulaId(lezione.getAula().getId());
        
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
        
        return lezione;
    }
} 