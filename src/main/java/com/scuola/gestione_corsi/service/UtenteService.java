package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.UtenteDTO;
import com.scuola.gestione_corsi.dto.UpdatePasswordDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.exception.UnauthorizedException;
import com.scuola.gestione_corsi.mapper.UtenteMapper;
import com.scuola.gestione_corsi.model.Utente;
import com.scuola.gestione_corsi.model.Ruolo;
import com.scuola.gestione_corsi.model.Studente;
import com.scuola.gestione_corsi.model.Docente;
import com.scuola.gestione_corsi.repository.UtenteRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteMapper utenteMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UtenteDTO> findAll() {
        return utenteRepository.findAll()
                .stream()
                .map(utenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UtenteDTO findById(Long id) {
        log.debug("Ricerca utente con ID: {}", id);
        return utenteRepository.findById(id)
                .map(utente -> {
                    log.debug("Utente trovato: {}", utente);
                    return utenteMapper.toDTO(utente);
                })
                .orElseThrow(() -> {
                    log.warn("Utente non trovato con ID: {}", id);
                    return new ResourceNotFoundException("Utente non trovato con ID: " + id);
                });
    }

    @PostConstruct
    public void init() {
        // Aggiorna gli utenti esistenti con valori di default per nome e cognome
        List<Utente> utenti = utenteRepository.findAll();
        for (Utente utente : utenti) {
            if (utente.getNome() == null) {
                utente.setNome("Utente");
            }
            if (utente.getCognome() == null) {
                utente.setCognome(utente.getEmail().split("@")[0]);
            }
        }
        utenteRepository.saveAll(utenti);
    }

    public UtenteDTO update(Long id, UtenteDTO dto) {
        log.debug("Tentativo di aggiornamento utente con id: {}", id);
        
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Utente non trovato con id: {}", id);
                    return new ResourceNotFoundException("Utente non trovato con id: " + id);
                });
        
        log.debug("Utente trovato: {}", utente.getEmail());
        
        // Verifica che i campi obbligatori siano presenti
        if (dto.getNome() == null || dto.getCognome() == null || dto.getEmail() == null) {
            throw new IllegalArgumentException("Nome, cognome ed email sono campi obbligatori");
        }
        
        // Aggiorna i campi base
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        
        // Aggiorna le relazioni se necessario
        if (utente.getStudente() != null) {
            log.debug("Aggiornamento dati studente associato");
            Studente studente = utente.getStudente();
            studente.setNome(dto.getNome());
            studente.setCognome(dto.getCognome());
            // Mantieni gli altri campi dello studente invariati
            utente.setStudente(studente);
        }
        
        if (utente.getDocente() != null) {
            log.debug("Aggiornamento dati docente associato");
            Docente docente = utente.getDocente();
            docente.setNome(dto.getNome());
            docente.setCognome(dto.getCognome());
            utente.setDocente(docente);
        }
        
        Utente updated = utenteRepository.save(utente);
        log.debug("Utente aggiornato con successo: {}", updated.getEmail());
        
        return utenteMapper.toDTO(updated);
    }

    public void updatePassword(Long id, UpdatePasswordDTO dto, String emailAdmin) {
        log.debug("Tentativo di aggiornamento password per utente con id: {} da parte dell'admin: {}", id, emailAdmin);
        
        // Verifica che l'utente esista
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Utente non trovato con id: {}", id);
                    return new ResourceNotFoundException("Utente non trovato con id: " + id);
                });
        
        // Aggiorna la password
        String nuovaPasswordCriptata = passwordEncoder.encode(dto.getNuovaPassword());
        utenteRepository.updatePassword(id, nuovaPasswordCriptata);
        
        log.debug("Password aggiornata con successo per l'utente: {}", utente.getEmail());
    }
} 