package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.PagamentoDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.PagamentoMapper;
import com.scuola.gestione_corsi.model.Pagamento;
import com.scuola.gestione_corsi.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    public List<PagamentoDTO> findAll() {
        return pagamentoRepository.findAll()
                .stream()
                .map(pagamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PagamentoDTO findById(Long id) {
        return pagamentoRepository.findById(id)
                .map(pagamentoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento non trovato con id: " + id));
    }

    public List<PagamentoDTO> findByIscrizioneId(Long iscrizioneId) {
        return pagamentoRepository.findByIscrizione_Id(iscrizioneId)
                .stream()
                .map(pagamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PagamentoDTO create(PagamentoDTO dto) {
        Pagamento pagamento = pagamentoMapper.toEntity(dto);
        Pagamento saved = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDTO(saved);
    }

    public PagamentoDTO update(Long id, PagamentoDTO dto) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pagamento non trovato con id: " + id);
        }
        dto.setId(id);
        Pagamento pagamento = pagamentoMapper.toEntity(dto);
        Pagamento updated = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pagamento non trovato con id: " + id);
        }
        pagamentoRepository.deleteById(id);
    }
} 