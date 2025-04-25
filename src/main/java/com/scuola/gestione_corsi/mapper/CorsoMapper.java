package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.CorsoDTO;
import com.scuola.gestione_corsi.model.Corso;
import com.scuola.gestione_corsi.model.Categoria;
import com.scuola.gestione_corsi.model.Docente;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/**
 * Mapper per l'entità Corso.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class CorsoMapper {
    
    /**
     * Converte un'entità Corso in un DTO.
     * @param corso L'entità da convertire
     * @return Il DTO convertito
     */
    public CorsoDTO toDTO(Corso corso) {
        if (corso == null) {
            return null;
        }
        
        CorsoDTO dto = new CorsoDTO();
        dto.setId(corso.getId());
        dto.setNome(corso.getNome());
        dto.setDescrizione(corso.getDescrizione());
        dto.setDurata(corso.getDurata());
        dto.setMaxStudenti(corso.getMaxStudenti());
        dto.setPrezzo(corso.getPrezzo());
        dto.setCategoriaId(corso.getCategoria().getId());
        
        // Aggiungo la lista degli ID dei docenti
        if (corso.getDocenti() != null) {
            dto.setDocenti(corso.getDocenti().stream()
                    .map(Docente::getId)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Corso.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Corso toEntity(CorsoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Corso corso = new Corso();
        corso.setId(dto.getId());
        corso.setNome(dto.getNome());
        corso.setDescrizione(dto.getDescrizione());
        corso.setDurata(dto.getDurata());
        corso.setMaxStudenti(dto.getMaxStudenti());
        corso.setPrezzo(dto.getPrezzo());
        
        // Aggiungo la categoria
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            corso.setCategoria(categoria);
        }
        
        return corso;
    }
} 