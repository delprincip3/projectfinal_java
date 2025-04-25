package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.DocenteDTO;
import com.scuola.gestione_corsi.model.Docente;
import com.scuola.gestione_corsi.model.Utente;
import com.scuola.gestione_corsi.repository.DocenteRepository;
import com.scuola.gestione_corsi.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocenteService {

    private final DocenteRepository docenteRepository;
    private final UtenteRepository utenteRepository;

    public List<DocenteDTO> findAll() {
        return docenteRepository.findAllWithCorsi().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DocenteDTO findById(Long id) {
        return docenteRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Docente non trovato"));
    }

    @Transactional
    public DocenteDTO create(DocenteDTO dto) {
        Utente utente = utenteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Docente docente = new Docente();
        docente.setNome(dto.getNome());
        docente.setCognome(dto.getCognome());
        docente.setSpecializzazione(dto.getSpecializzazione());
        docente.setCv(dto.getCv());
        docente.setTariffa(dto.getTariffa());
        docente.setUtente(utente);

        return convertToDTO(docenteRepository.save(docente));
    }

    @Transactional
    public DocenteDTO update(Long id, DocenteDTO dto) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente non trovato"));

        docente.setNome(dto.getNome());
        docente.setCognome(dto.getCognome());
        docente.setSpecializzazione(dto.getSpecializzazione());
        docente.setCv(dto.getCv());
        docente.setTariffa(dto.getTariffa());

        return convertToDTO(docenteRepository.save(docente));
    }

    @Transactional
    public void delete(Long id) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente non trovato"));
        
        // Elimina prima le lezioni associate
        if (docente.getLezioni() != null) {
            docente.getLezioni().clear();
        }
        
        // Elimina le associazioni con i corsi
        if (docente.getCorsi() != null) {
            docente.getCorsi().clear();
        }
        
        // Elimina il docente
        docenteRepository.delete(docente);
        
        // Elimina l'utente associato
        utenteRepository.delete(docente.getUtente());
    }

    public List<DocenteDTO> findBySpecializzazione(String specializzazione) {
        return docenteRepository.findBySpecializzazione(specializzazione).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DocenteDTO convertToDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .nome(docente.getNome())
                .cognome(docente.getCognome())
                .email(docente.getUtente().getEmail())
                .specializzazione(docente.getSpecializzazione())
                .cv(docente.getCv())
                .tariffa(docente.getTariffa())
                .utenteId(docente.getUtente().getId())
                .build();
    }
} 