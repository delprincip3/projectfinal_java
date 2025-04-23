package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.ValutazioneDTO;
import com.scuola.gestione_corsi.model.Valutazione;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Valutazione.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class ValutazioneMapper {
    
    /**
     * Converte un'entità Valutazione in un DTO.
     * @param valutazione L'entità da convertire
     * @return Il DTO convertito
     */
    public ValutazioneDTO toDTO(Valutazione valutazione) {
        if (valutazione == null) {
            return null;
        }
        
        ValutazioneDTO dto = new ValutazioneDTO();
        dto.setId(valutazione.getId());
        dto.setVoto(valutazione.getVoto());
        dto.setCommento(valutazione.getCommento());
        dto.setDataValutazione(valutazione.getDataValutazione());
        dto.setIscrizioneId(valutazione.getIscrizione().getId());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Valutazione.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Valutazione toEntity(ValutazioneDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Valutazione valutazione = new Valutazione();
        valutazione.setId(dto.getId());
        valutazione.setVoto(dto.getVoto());
        valutazione.setCommento(dto.getCommento());
        valutazione.setDataValutazione(dto.getDataValutazione());
        
        return valutazione;
    }
} 