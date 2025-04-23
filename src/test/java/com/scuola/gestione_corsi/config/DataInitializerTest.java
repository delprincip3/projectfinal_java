package com.scuola.gestione_corsi.config;

import com.scuola.gestione_corsi.model.*;
import com.scuola.gestione_corsi.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private IscrizioneRepository iscrizioneRepository;

    @Autowired
    private LezioneRepository lezioneRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private MaterialeDidatticoRepository materialeDidatticoRepository;

    @Autowired
    private ValutazioneRepository valutazioneRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PresenzaRepository presenzaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Puliamo i repository prima di ogni test
        presenzaRepository.deleteAll();
        pagamentoRepository.deleteAll();
        valutazioneRepository.deleteAll();
        iscrizioneRepository.deleteAll();
        materialeDidatticoRepository.deleteAll();
        lezioneRepository.deleteAll();
        aulaRepository.deleteAll();
        corsoRepository.deleteAll();
        categoriaRepository.deleteAll();
        docenteRepository.deleteAll();
        studenteRepository.deleteAll();
        utenteRepository.deleteAll();
    }

    @Test
    void testInitData() throws Exception {
        // Eseguiamo l'inizializzazione
        CommandLineRunner runner = dataInitializer.initData(
            utenteRepository,
            studenteRepository,
            docenteRepository,
            categoriaRepository,
            corsoRepository,
            iscrizioneRepository,
            lezioneRepository,
            aulaRepository,
            materialeDidatticoRepository,
            valutazioneRepository,
            pagamentoRepository,
            presenzaRepository
        );
        
        // Eseguiamo il CommandLineRunner
        runner.run();

        // Verifichiamo che gli utenti siano stati creati
        assertTrue(utenteRepository.existsByEmail("admin@scuola.it"));
        assertTrue(utenteRepository.existsByEmail("docente@scuola.it"));
        assertTrue(utenteRepository.existsByEmail("studente@scuola.it"));

        // Verifichiamo che lo studente sia stato creato
        Utente studenteUtente = utenteRepository.findByEmail("studente@scuola.it").orElseThrow();
        assertTrue(studenteRepository.existsByUtente(studenteUtente));

        // Verifichiamo che il docente sia stato creato
        Utente docenteUtente = utenteRepository.findByEmail("docente@scuola.it").orElseThrow();
        assertTrue(docenteRepository.existsByUtente(docenteUtente));

        // Verifichiamo che la categoria sia stata creata
        assertTrue(categoriaRepository.existsByNome("Programmazione"));

        // Verifichiamo che il corso sia stato creato
        assertTrue(corsoRepository.existsByNome("Java Advanced"));

        // Verifichiamo che l'aula sia stata creata
        assertTrue(aulaRepository.existsByNome("Aula 1"));

        // Verifichiamo che la lezione sia stata creata
        assertTrue(lezioneRepository.existsByTitolo("Introduzione a Java"));

        // Verifichiamo che il materiale didattico sia stato creato
        assertTrue(materialeDidatticoRepository.existsByTitolo("Slide Java"));

        // Verifichiamo che l'iscrizione sia stata creata
        Studente studente = studenteRepository.findByUtente(studenteUtente).orElseThrow();
        Corso corso = corsoRepository.findByNome("Java Advanced").orElseThrow();
        assertTrue(iscrizioneRepository.existsByStudenteAndCorso(studente, corso));

        // Verifichiamo che la valutazione sia stata creata
        Iscrizione iscrizione = iscrizioneRepository.findByStudenteAndCorso(studente, corso).orElseThrow();
        assertTrue(valutazioneRepository.existsByIscrizione(iscrizione));

        // Verifichiamo che il pagamento sia stato creato
        assertTrue(pagamentoRepository.existsByIscrizione(iscrizione));

        // Verifichiamo che la presenza sia stata creata
        Lezione lezione = lezioneRepository.findByTitolo("Introduzione a Java").orElseThrow();
        assertTrue(presenzaRepository.existsByIscrizioneAndLezione(iscrizione, lezione));
    }

    @Test
    void testInitDataIdempotency() {
        // Eseguiamo l'inizializzazione due volte
        dataInitializer.initData(
            utenteRepository,
            studenteRepository,
            docenteRepository,
            categoriaRepository,
            corsoRepository,
            iscrizioneRepository,
            lezioneRepository,
            aulaRepository,
            materialeDidatticoRepository,
            valutazioneRepository,
            pagamentoRepository,
            presenzaRepository
        );

        // Salviamo il conteggio attuale
        long utentiCount = utenteRepository.count();
        long studentiCount = studenteRepository.count();
        long docentiCount = docenteRepository.count();
        long categorieCount = categoriaRepository.count();
        long corsiCount = corsoRepository.count();
        long iscrizioniCount = iscrizioneRepository.count();
        long lezioniCount = lezioneRepository.count();
        long auleCount = aulaRepository.count();
        long materialiCount = materialeDidatticoRepository.count();
        long valutazioniCount = valutazioneRepository.count();
        long pagamentiCount = pagamentoRepository.count();
        long presenzeCount = presenzaRepository.count();

        // Eseguiamo l'inizializzazione una seconda volta
        dataInitializer.initData(
            utenteRepository,
            studenteRepository,
            docenteRepository,
            categoriaRepository,
            corsoRepository,
            iscrizioneRepository,
            lezioneRepository,
            aulaRepository,
            materialeDidatticoRepository,
            valutazioneRepository,
            pagamentoRepository,
            presenzaRepository
        );

        // Verifichiamo che i conteggi non siano cambiati
        assertEquals(utentiCount, utenteRepository.count());
        assertEquals(studentiCount, studenteRepository.count());
        assertEquals(docentiCount, docenteRepository.count());
        assertEquals(categorieCount, categoriaRepository.count());
        assertEquals(corsiCount, corsoRepository.count());
        assertEquals(iscrizioniCount, iscrizioneRepository.count());
        assertEquals(lezioniCount, lezioneRepository.count());
        assertEquals(auleCount, aulaRepository.count());
        assertEquals(materialiCount, materialeDidatticoRepository.count());
        assertEquals(valutazioniCount, valutazioneRepository.count());
        assertEquals(pagamentiCount, pagamentoRepository.count());
        assertEquals(presenzeCount, presenzaRepository.count());
    }
} 