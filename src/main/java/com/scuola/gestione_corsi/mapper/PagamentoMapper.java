package com.scuola.gestione_corsi.mapper;

import com.scuola.gestione_corsi.dto.PagamentoDTO;
import com.scuola.gestione_corsi.model.Pagamento;
import com.scuola.gestione_corsi.model.Iscrizione;
import org.springframework.stereotype.Component;

/**
 * Mapper per l'entità Pagamento.
 * Fornisce metodi per la conversione tra entità e DTO.
 */
@Component
public class PagamentoMapper {
    
    /**
     * Converte un'entità Pagamento in un DTO.
     * @param pagamento L'entità da convertire
     * @return Il DTO convertito
     */
    public PagamentoDTO toDTO(Pagamento pagamento) {
        if (pagamento == null) {
            return null;
        }
        
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(pagamento.getId());
        dto.setImporto(pagamento.getImporto());
        dto.setDataPagamento(pagamento.getDataPagamento());
        dto.setMetodoPagamento(pagamento.getMetodoPagamento());
        dto.setIscrizioneId(pagamento.getIscrizione().getId());
        
        return dto;
    }
    
    /**
     * Converte un DTO in un'entità Pagamento.
     * @param dto Il DTO da convertire
     * @return L'entità convertita
     */
    public Pagamento toEntity(PagamentoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Pagamento pagamento = new Pagamento();
        pagamento.setId(dto.getId());
        pagamento.setImporto(dto.getImporto());
        pagamento.setDataPagamento(dto.getDataPagamento());
        pagamento.setMetodoPagamento(dto.getMetodoPagamento());
        
        // Imposta la relazione con l'iscrizione
        if (dto.getIscrizioneId() != null) {
            Iscrizione iscrizione = new Iscrizione();
            iscrizione.setId(dto.getIscrizioneId());
            pagamento.setIscrizione(iscrizione);
        }
        
        return pagamento;
    }
} 