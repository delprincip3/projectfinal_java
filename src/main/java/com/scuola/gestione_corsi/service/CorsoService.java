package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.CorsoDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.CorsoMapper;
import com.scuola.gestione_corsi.model.Corso;
import com.scuola.gestione_corsi.model.Categoria;
import com.scuola.gestione_corsi.model.Docente;
import com.scuola.gestione_corsi.repository.CorsoRepository;
import com.scuola.gestione_corsi.repository.DocenteRepository;
import com.scuola.gestione_corsi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CorsoService {

    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private CorsoMapper corsoMapper;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll()
                .stream()
                .map(corsoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CorsoDTO findById(Long id) {
        return corsoRepository.findById(id)
                .map(corsoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Corso non trovato con id: " + id));
    }

    public List<CorsoDTO> findByCategoriaId(Long categoriaId) {
        return corsoRepository.findByCategoria_Id(categoriaId)
                .stream()
                .map(corsoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CorsoDTO> findByDocenteId(Long docenteId) {
        return corsoRepository.findByDocenti_Id(docenteId)
                .stream()
                .map(corsoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CorsoDTO create(CorsoDTO dto) {
        Corso corso = new Corso();
        corso.setNome(dto.getNome());
        corso.setDescrizione(dto.getDescrizione());
        corso.setDurata(dto.getDurata());
        corso.setMaxStudenti(dto.getMaxStudenti());
        corso.setPrezzo(dto.getPrezzo());
        
        // Imposta la categoria
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria non trovata con id: " + dto.getCategoriaId()));
            corso.setCategoria(categoria);
        }
        
        // Imposta i docenti
        if (dto.getDocenti() != null && !dto.getDocenti().isEmpty()) {
            List<Docente> docenti = docenteRepository.findAllById(dto.getDocenti());
            corso.setDocenti(docenti);
        }
        
        Corso saved = corsoRepository.save(corso);
        return corsoMapper.toDTO(saved);
    }

    @Transactional
    public CorsoDTO update(Long id, CorsoDTO dto) {
        Corso corso = corsoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Corso non trovato con id: " + id));

        corso.setNome(dto.getNome());
        corso.setDescrizione(dto.getDescrizione());
        corso.setDurata(dto.getDurata());
        corso.setMaxStudenti(dto.getMaxStudenti());
        corso.setPrezzo(dto.getPrezzo());
        
        // Aggiorno la categoria
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria non trovata con id: " + dto.getCategoriaId()));
            corso.setCategoria(categoria);
        }
        
        // Aggiorno i docenti
        if (dto.getDocenti() != null) {
            List<Docente> docenti = docenteRepository.findAllById(dto.getDocenti());
            corso.setDocenti(docenti);
        }

        Corso updated = corsoRepository.save(corso);
        return corsoMapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!corsoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Corso non trovato con id: " + id);
        }
        corsoRepository.deleteById(id);
    }
} 