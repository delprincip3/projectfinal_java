package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.StudenteDTO;
import com.scuola.gestione_corsi.model.Studente;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Studente.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class StudenteMapper {
    
    /**
     * Converte un'entità Studente in un DTO.
     * @param studente L'entità da convertire
     * @return Il DTO convertito
     */
    public StudenteDTO toDTO(Studente studente) {
        if (studente == null) {
            return null;
        }
        
        StudenteDTO dto = new StudenteDTO();
        dto.setId(studente.getId());
        dto.setNome(studente.getNome());
        dto.setCognome(studente.getCognome());
        dto.setDataNascita(studente.getDataNascita());
        dto.setIndirizzo(studente.getIndirizzo());
        dto.setTelefono(studente.getTelefono());
        dto.setUtenteId(studente.getUtente().getId());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Studente.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Studente toEntity(StudenteDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Studente studente = new Studente();
        studente.setId(dto.getId());
        studente.setNome(dto.getNome());
        studente.setCognome(dto.getCognome());
        studente.setDataNascita(dto.getDataNascita());
        studente.setIndirizzo(dto.getIndirizzo());
        studente.setTelefono(dto.getTelefono());
        
        return studente;
    }
} 