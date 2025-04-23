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
            // Creazione utenti
            Utente admin = Utente.builder()
                    .email("admin@scuola.it")
                    .password(passwordEncoder.encode("admin"))
                    .ruolo(Ruolo.ADMIN)
                    .build();
            utenteRepository.save(admin);

            Utente docente = Utente.builder()
                    .email("docente@scuola.it")
                    .password(passwordEncoder.encode("docente"))
                    .ruolo(Ruolo.DOCENTE)
                    .build();
            utenteRepository.save(docente);

            Utente studente = Utente.builder()
                    .email("studente@scuola.it")
                    .password(passwordEncoder.encode("studente"))
                    .ruolo(Ruolo.STUDENTE)
                    .build();
            utenteRepository.save(studente);

            // Creazione studenti
            Studente studente1 = Studente.builder()
                    .nome("Mario")
                    .cognome("Rossi")
                    .dataNascita(LocalDate.of(2000, 1, 1))
                    .indirizzo("Via Roma 1")
                    .telefono("1234567890")
                    .utente(studente)
                    .build();
            studenteRepository.save(studente1);

            // Creazione docenti
            Docente docente1 = Docente.builder()
                    .nome("Luigi")
                    .cognome("Bianchi")
                    .specializzazione("Programmazione Java")
                    .cv("Esperto in sviluppo Java con 10 anni di esperienza")
                    .tariffa(50.0)
                    .utente(docente)
                    .build();
            docenteRepository.save(docente1);

            // Creazione categorie
            Categoria programmazione = Categoria.builder()
                    .nome("Programmazione")
                    .descrizione("Corsi di programmazione")
                    .icona("code")
                    .build();
            categoriaRepository.save(programmazione);

            // Creazione corsi
            Corso corsoJava = Corso.builder()
                    .nome("Java Advanced")
                    .descrizione("Corso avanzato di programmazione Java")
                    .durata(120)
                    .maxStudenti(20)
                    .prezzo(1000.0)
                    .categoria(programmazione)
                    .docenti(List.of(docente1))
                    .build();
            corsoRepository.save(corsoJava);

            // Creazione aule
            Aula aula1 = Aula.builder()
                    .nome("Aula 1")
                    .capienza(30)
                    .build();
            aulaRepository.save(aula1);

            // Creazione lezioni
            Lezione lezione1 = Lezione.builder()
                    .titolo("Introduzione a Java")
                    .descrizione("Prima lezione del corso Java")
                    .dataOra(LocalDateTime.now().plusDays(1))
                    .durata(120)
                    .corso(corsoJava)
                    .docente(docente1)
                    .aula(aula1)
                    .build();
            lezioneRepository.save(lezione1);

            // Creazione materiale didattico
            MaterialeDidattico materiale1 = MaterialeDidattico.builder()
                    .titolo("Slide Java")
                    .tipo("PDF")
                    .url("http://example.com/slide.pdf")
                    .lezione(lezione1)
                    .build();
            materialeDidatticoRepository.save(materiale1);

            // Creazione iscrizioni
            Iscrizione iscrizione1 = Iscrizione.builder()
                    .dataIscrizione(LocalDateTime.now())
                    .stato(StatoIscrizione.ATTIVA)
                    .metodoPagamento("Carta di credito")
                    .studente(studente1)
                    .corso(corsoJava)
                    .build();
            iscrizioneRepository.save(iscrizione1);

            // Creazione valutazioni
            Valutazione valutazione1 = Valutazione.builder()
                    .voto(28)
                    .commento("Ottimo studente")
                    .dataValutazione(LocalDate.now())
                    .iscrizione(iscrizione1)
                    .build();
            valutazioneRepository.save(valutazione1);

            // Creazione pagamenti
            Pagamento pagamento1 = Pagamento.builder()
                    .importo(1000.0)
                    .dataPagamento(LocalDate.now())
                    .metodoPagamento("Carta di credito")
                    .iscrizione(iscrizione1)
                    .build();
            pagamentoRepository.save(pagamento1);

            // Creazione presenze
            Presenza presenza1 = Presenza.builder()
                    .dataPresenza(LocalDate.now())
                    .presente(true)
                    .iscrizione(iscrizione1)
                    .lezione(lezione1)
                    .build();
            presenzaRepository.save(presenza1);
        };
    }
} 