package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.MaterialeDidatticoDTO;
import com.scuola.gestione_corsi.model.MaterialeDidattico;
import com.scuola.gestione_corsi.model.Lezione;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità MaterialeDidattico.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class MaterialeDidatticoMapper {
    
    /**
     * Converte un'entità MaterialeDidattico in un DTO.
     * @param materiale L'entità da convertire
     * @return Il DTO convertito
     */
    public MaterialeDidatticoDTO toDTO(MaterialeDidattico materiale) {
        if (materiale == null) {
            return null;
        }
        
        MaterialeDidatticoDTO dto = new MaterialeDidatticoDTO();
        dto.setId(materiale.getId());
        dto.setTitolo(materiale.getTitolo());
        dto.setTipo(materiale.getTipo());
        dto.setUrl(materiale.getUrl());
        dto.setLezioneId(materiale.getLezione().getId());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità MaterialeDidattico.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public MaterialeDidattico toEntity(MaterialeDidatticoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        MaterialeDidattico materiale = new MaterialeDidattico();
        materiale.setId(dto.getId());
        materiale.setTitolo(dto.getTitolo());
        materiale.setTipo(dto.getTipo());
        materiale.setUrl(dto.getUrl());
        
        // Imposta la relazione con la lezione
        if (dto.getLezioneId() != null) {
            Lezione lezione = new Lezione();
            lezione.setId(dto.getLezioneId());
            materiale.setLezione(lezione);
        }
        
        return materiale;
    }
} 