package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.DocenteDTO;
import com.scuola.gestione_corsi.model.Docente;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Docente.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class DocenteMapper {
    
    /**
     * Converte un'entità Docente in un DTO.
     * @param docente L'entità da convertire
     * @return Il DTO convertito
     */
    public DocenteDTO toDTO(Docente docente) {
        if (docente == null) {
            return null;
        }
        
        DocenteDTO dto = new DocenteDTO();
        dto.setId(docente.getId());
        dto.setNome(docente.getNome());
        dto.setCognome(docente.getCognome());
        dto.setSpecializzazione(docente.getSpecializzazione());
        dto.setCv(docente.getCv());
        dto.setTariffa(docente.getTariffa());
        dto.setUtenteId(docente.getUtente().getId());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Docente.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Docente toEntity(DocenteDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Docente docente = new Docente();
        docente.setId(dto.getId());
        docente.setNome(dto.getNome());
        docente.setCognome(dto.getCognome());
        docente.setSpecializzazione(dto.getSpecializzazione());
        docente.setCv(dto.getCv());
        docente.setTariffa(dto.getTariffa());
        
        return docente;
    }
} 