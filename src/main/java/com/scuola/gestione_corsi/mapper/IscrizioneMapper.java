package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.IscrizioneDTO;
import com.scuola.gestione_corsi.model.Iscrizione;
import com.scuola.gestione_corsi.model.Studente;
import com.scuola.gestione_corsi.model.Corso;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Iscrizione.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class IscrizioneMapper {
    
    /**
     * Converte un'entità Iscrizione in un DTO.
     * @param iscrizione L'entità da convertire
     * @return Il DTO convertito
     */
    public IscrizioneDTO toDTO(Iscrizione iscrizione) {
        if (iscrizione == null) {
            return null;
        }
        
        IscrizioneDTO dto = new IscrizioneDTO();
        dto.setId(iscrizione.getId());
        dto.setDataIscrizione(iscrizione.getDataIscrizione());
        dto.setStato(iscrizione.getStato());
        dto.setMetodoPagamento(iscrizione.getMetodoPagamento());
        dto.setStudenteId(iscrizione.getStudente().getId());
        dto.setCorsoId(iscrizione.getCorso().getId());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Iscrizione.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Iscrizione toEntity(IscrizioneDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Iscrizione iscrizione = new Iscrizione();
        iscrizione.setId(dto.getId());
        iscrizione.setDataIscrizione(dto.getDataIscrizione());
        iscrizione.setStato(dto.getStato());
        iscrizione.setMetodoPagamento(dto.getMetodoPagamento());
        
        // Imposta lo studente
        Studente studente = new Studente();
        studente.setId(dto.getStudenteId());
        iscrizione.setStudente(studente);
        
        // Imposta il corso
        Corso corso = new Corso();
        corso.setId(dto.getCorsoId());
        iscrizione.setCorso(corso);
        
        return iscrizione;
    }
} 