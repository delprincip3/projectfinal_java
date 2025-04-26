package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.PresenzaDTO;
import com.scuola.gestione_corsi.model.Presenza;
import com.scuola.gestione_corsi.model.Iscrizione;
import com.scuola.gestione_corsi.model.Lezione;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Presenza.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class PresenzaMapper {
    
    /**
     * Converte un'entità Presenza in un DTO.
     * @param presenza L'entità da convertire
     * @return Il DTO convertito
     */
    public PresenzaDTO toDTO(Presenza presenza) {
        if (presenza == null) {
            return null;
        }
        
        PresenzaDTO dto = new PresenzaDTO();
        dto.setId(presenza.getId());
        dto.setDataPresenza(presenza.getDataPresenza());
        dto.setPresente(presenza.getPresente());
        dto.setIscrizioneId(presenza.getIscrizione().getId());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Presenza.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Presenza toEntity(PresenzaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Presenza presenza = new Presenza();
        presenza.setId(dto.getId());
        presenza.setDataPresenza(dto.getDataPresenza());
        presenza.setPresente(dto.getPresente());
        
        // Imposta la relazione con l'iscrizione
        if (dto.getIscrizioneId() != null) {
            Iscrizione iscrizione = new Iscrizione();
            iscrizione.setId(dto.getIscrizioneId());
            presenza.setIscrizione(iscrizione);
        }
        
        // Imposta la relazione con la lezione
        if (dto.getLezioneId() != null) {
            Lezione lezione = new Lezione();
            lezione.setId(dto.getLezioneId());
            presenza.setLezione(lezione);
        }
        
        return presenza;
    }
} 