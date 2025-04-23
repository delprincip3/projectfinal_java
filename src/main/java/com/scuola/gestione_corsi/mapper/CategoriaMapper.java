package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.CategoriaDTO;
import com.scuola.gestione_corsi.model.Categoria;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Categoria.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class CategoriaMapper {
    
    /**
     * Converte un'entità Categoria in un DTO.
     * @param categoria L'entità da convertire
     * @return Il DTO convertito
     */
    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setDescrizione(categoria.getDescrizione());
        dto.setIcona(categoria.getIcona());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Categoria.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNome(dto.getNome());
        categoria.setDescrizione(dto.getDescrizione());
        categoria.setIcona(dto.getIcona());
        
        return categoria;
    }
} 