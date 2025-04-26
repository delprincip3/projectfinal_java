package com.scuola.gestione_corsi.config;

import com.scuola.gestione_corsi.model.*;
import com.scuola.gestione_corsi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(
            UtenteRepository utenteRepository,
            StudenteRepository studenteRepository,
            DocenteRepository docenteRepository,
            CategoriaRepository categoriaRepository,
            CorsoRepository corsoRepository,
            IscrizioneRepository iscrizioneRepository,
            LezioneRepository lezioneRepository,
            AulaRepository aulaRepository,
            MaterialeDidatticoRepository materialeDidatticoRepository,
            ValutazioneRepository valutazioneRepository,
            PagamentoRepository pagamentoRepository,
            PresenzaRepository presenzaRepository
    ) {
        return args -> {
            // Creazione utenti solo se non esistono già
            Utente admin = null;
            Utente docente = null;
            Utente studente = null;

            if (!utenteRepository.existsByEmail("admin@scuola.it")) {
                admin = Utente.builder()
                        .email("admin@scuola.it")
                        .password(passwordEncoder.encode("admin"))
                        .ruolo(Ruolo.ADMIN)
                        .build();
                utenteRepository.save(admin);
            } else {
                admin = utenteRepository.findByEmail("admin@scuola.it").orElseThrow();
            }

            if (!utenteRepository.existsByEmail("docente@scuola.it")) {
                docente = Utente.builder()
                        .email("docente@scuola.it")
                        .password(passwordEncoder.encode("docente"))
                        .ruolo(Ruolo.DOCENTE)
                        .build();
                utenteRepository.save(docente);
            } else {
                docente = utenteRepository.findByEmail("docente@scuola.it").orElseThrow();
            }

            if (!utenteRepository.existsByEmail("studente@scuola.it")) {
                studente = Utente.builder()
                        .email("studente@scuola.it")
                        .password(passwordEncoder.encode("studente"))
                        .ruolo(Ruolo.STUDENTE)
                        .build();
                utenteRepository.save(studente);
            } else {
                studente = utenteRepository.findByEmail("studente@scuola.it").orElseThrow();
            }

            // Creazione studenti
            Studente studente1 = null;
            if (!studenteRepository.existsByUtente(studente)) {
                studente1 = Studente.builder()
                        .nome("Mario")
                        .cognome("Rossi")
                        .dataNascita(LocalDate.of(2000, 1, 1))
                        .indirizzo("Via Roma 1")
                        .telefono("1234567890")
                        .utente(studente)
                        .build();
                studenteRepository.save(studente1);
            } else {
                studente1 = studenteRepository.findByUtente(studente).orElseThrow();
            }

            // Creazione docenti
            Docente docente1 = null;
            if (!docenteRepository.existsByUtente(docente)) {
                docente1 = Docente.builder()
                        .nome("Luigi")
                        .cognome("Bianchi")
                        .specializzazione("Programmazione Java")
                        .cv("Esperto in sviluppo Java con 10 anni di esperienza")
                        .tariffa(50.0)
                        .utente(docente)
                        .build();
                docenteRepository.save(docente1);
            } else {
                docente1 = docenteRepository.findByUtente(docente).orElseThrow();
            }

            // Creazione categorie
            Categoria programmazione = null;
            if (!categoriaRepository.existsByNome("Programmazione")) {
                programmazione = Categoria.builder()
                        .nome("Programmazione")
                        .descrizione("Corsi di programmazione")
                        .icona("code")
                        .build();
                categoriaRepository.save(programmazione);
            } else {
                programmazione = categoriaRepository.findByNome("Programmazione").orElseThrow();
            }

            // Creazione corsi
            Corso corsoJava = null;
            if (!corsoRepository.existsByNome("Java Advanced")) {
                corsoJava = Corso.builder()
                        .nome("Java Advanced")
                        .descrizione("Corso avanzato di programmazione Java")
                        .durata(120)
                        .maxStudenti(20)
                        .prezzo(1000.0)
                        .categoria(programmazione)
                        .docenti(List.of(docente1))
                        .build();
                corsoRepository.save(corsoJava);
            } else {
                corsoJava = corsoRepository.findByNome("Java Advanced").orElseThrow();
            }

            // Creazione aule
            Aula aula1 = null;
            if (!aulaRepository.existsByNome("Aula 1")) {
                aula1 = Aula.builder()
                        .nome("Aula 1")
                        .capienza(30)
                        .build();
                aulaRepository.save(aula1);
            } else {
                aula1 = aulaRepository.findByNome("Aula 1").orElseThrow();
            }

            // Creazione lezioni
            Lezione lezione1 = null;
            if (!lezioneRepository.existsByTitolo("Introduzione a Java")) {
                lezione1 = Lezione.builder()
                        .titolo("Introduzione a Java")
                        .descrizione("Prima lezione del corso Java")
                        .dataOra(LocalDateTime.now().plusDays(1))
                        .durata(120)
                        .corso(corsoJava)
                        .docente(docente1)
                        .aula(aula1)
                        .build();
                lezioneRepository.save(lezione1);
            } else {
                lezione1 = lezioneRepository.findByTitolo("Introduzione a Java").orElseThrow();
            }

            // Creazione materiale didattico
            MaterialeDidattico materiale1 = null;
            if (!materialeDidatticoRepository.existsByTitolo("Slide Java")) {
                materiale1 = MaterialeDidattico.builder()
                        .titolo("Slide Java")
                        .tipo("PDF")
                        .url("http://example.com/slide.pdf")
                        .lezione(lezione1)
                        .build();
                materialeDidatticoRepository.save(materiale1);
            } else {
                materiale1 = materialeDidatticoRepository.findByTitolo("Slide Java").orElseThrow();
            }

            // Creazione iscrizioni
            final Iscrizione iscrizione1;
            if (!iscrizioneRepository.existsByStudenteAndCorso(studente1, corsoJava)) {
                iscrizione1 = Iscrizione.builder()
                        .dataIscrizione(LocalDateTime.now())
                        .stato(StatoIscrizione.ATTIVA)
                        .metodoPagamento("Carta di credito")
                        .studente(studente1)
                        .corso(corsoJava)
                        .build();
                iscrizioneRepository.save(iscrizione1);
            } else {
                iscrizione1 = iscrizioneRepository.findFirstByStudenteAndCorso(studente1, corsoJava).orElseThrow();
            }

            // Creazione valutazioni
            Valutazione valutazione1 = null;
            if (!valutazioneRepository.existsByIscrizione(iscrizione1)) {
                valutazione1 = Valutazione.builder()
                        .voto(28)
                        .commento("Ottimo studente")
                        .dataValutazione(LocalDate.now())
                        .iscrizione(iscrizione1)
                        .build();
                valutazioneRepository.save(valutazione1);
            } else {
                valutazione1 = valutazioneRepository.findFirstByIscrizioneOrderByDataValutazioneDesc(iscrizione1)
                        .orElseGet(() -> {
                            Valutazione v = Valutazione.builder()
                                    .voto(28)
                                    .commento("Ottimo studente")
                                    .dataValutazione(LocalDate.now())
                                    .iscrizione(iscrizione1)
                                    .build();
                            return valutazioneRepository.save(v);
                        });
            }

            // Creazione pagamenti
            Pagamento pagamento1 = null;
            if (!pagamentoRepository.existsByIscrizione(iscrizione1)) {
                pagamento1 = Pagamento.builder()
                        .importo(1000.0)
                        .dataPagamento(LocalDate.now())
                        .metodoPagamento("Carta di credito")
                        .iscrizione(iscrizione1)
                        .build();
                pagamentoRepository.save(pagamento1);
            } else {
                pagamento1 = pagamentoRepository.findFirstByIscrizioneOrderByDataPagamentoDesc(iscrizione1)
                        .orElseThrow();
            }

            // Creazione presenze
            Presenza presenza1 = null;
            if (!presenzaRepository.existsByIscrizioneAndLezione(iscrizione1, lezione1)) {
                presenza1 = Presenza.builder()
                        .dataPresenza(LocalDate.now())
                        .presente(true)
                        .iscrizione(iscrizione1)
                        .lezione(lezione1)
                        .build();
                presenzaRepository.save(presenza1);
            } else {
                presenza1 = presenzaRepository.findByIscrizioneAndLezione(iscrizione1, lezione1).orElseThrow();
            }
        };
    }
} 