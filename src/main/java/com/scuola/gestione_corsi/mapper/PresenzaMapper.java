package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.PresenzaDTO;
import com.scuola.gestione_corsi.model.Presenza;
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
        
        return presenza;
    }
} 