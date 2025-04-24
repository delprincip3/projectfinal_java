package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.StudenteDTO;
import com.scuola.gestione_corsi.exception.ResourceNotFoundException;
import com.scuola.gestione_corsi.mapper.StudenteMapper;
import com.scuola.gestione_corsi.model.Studente;
import com.scuola.gestione_corsi.model.Utente;
import com.scuola.gestione_corsi.repository.StudenteRepository;
import com.scuola.gestione_corsi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private StudenteMapper studenteMapper;

    public List<StudenteDTO> findAll() {
        return studenteRepository.findAll()
                .stream()
                .map(studenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public StudenteDTO findById(Long id) {
        return studenteRepository.findById(id)
                .map(studenteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Studente non trovato con id: " + id));
    }

    @Transactional
    public StudenteDTO create(StudenteDTO dto) {
        Utente utente = utenteRepository.findById(dto.getUtenteId())
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + dto.getUtenteId()));

        Studente studente = studenteMapper.toEntity(dto);
        studente.setUtente(utente);

        return studenteMapper.toDTO(studenteRepository.save(studente));
    }

    @Transactional
    public StudenteDTO update(Long id, StudenteDTO dto) {
        Studente studente = studenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studente non trovato con id: " + id));

        studente.setNome(dto.getNome());
        studente.setCognome(dto.getCognome());
        studente.setDataNascita(dto.getDataNascita());
        studente.setIndirizzo(dto.getIndirizzo());
        studente.setTelefono(dto.getTelefono());

        return studenteMapper.toDTO(studenteRepository.save(studente));
    }

    public void delete(Long id) {
        if (!studenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Studente non trovato con id: " + id);
        }
        studenteRepository.deleteById(id);
    }

    public List<StudenteDTO> findByCorsoId(Long corsoId) {
        return studenteRepository.findByIscrizioni_Corso_Id(corsoId)
                .stream()
                .map(studenteMapper::toDTO)
                .collect(Collectors.toList());
    }
} 