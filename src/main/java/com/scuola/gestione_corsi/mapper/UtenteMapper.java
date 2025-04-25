package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.UtenteDTO;
import com.scuola.gestione_corsi.model.Utente;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Utente.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class UtenteMapper {
    
    /**
     * Converte un'entità Utente in un DTO.
     * @param utente L'entità da convertire
     * @return Il DTO convertito
     */
    public UtenteDTO toDTO(Utente utente) {
        if (utente == null) {
            return null;
        }
        
        UtenteDTO dto = new UtenteDTO();
        dto.setId(utente.getId());
        dto.setNome(utente.getNome());
        dto.setCognome(utente.getCognome());
        dto.setEmail(utente.getEmail());
        dto.setRuolo(utente.getRuolo());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Utente.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Utente toEntity(UtenteDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Utente utente = new Utente();
        utente.setId(dto.getId());
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        utente.setRuolo(dto.getRuolo());
        
        return utente;
    }
} 