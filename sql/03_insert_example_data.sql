-- Inserimento dati di esempio

-- Inserimento utenti di esempio
INSERT INTO utenti (username, password, email, nome, cognome, ruolo) VALUES
('admin', '$2a$10$X7G3YbGJNQH6YQZ8V9W0Ue.9Z8X7Y6V5B4N3M2L1K0J9I8H7G6F5E4D3C2B1A', 'admin@scuola.it', 'Admin', 'System', 'ADMIN'),
('docente1', '$2a$10$X7G3YbGJNQH6YQZ8V9W0Ue.9Z8X7Y6V5B4N3M2L1K0J9I8H7G6F5E4D3C2B1A', 'docente1@scuola.it', 'Mario', 'Rossi', 'DOCENTE'),
('studente1', '$2a$10$X7G3YbGJNQH6YQZ8V9W0Ue.9Z8X7Y6V5B4N3M2L1K0J9I8H7G6F5E4D3C2B1A', 'studente1@scuola.it', 'Luigi', 'Bianchi', 'STUDENTE');

-- Inserimento corsi di esempio
INSERT INTO corsi (titolo, descrizione, durata_ore, data_inizio, data_fine, costo, max_partecipanti, stato) VALUES
('Java Avanzato', 'Corso avanzato di programmazione Java', 120, '2024-05-01', '2024-08-01', 1500.00, 20, 'ATTIVO'),
('Spring Boot', 'Sviluppo applicazioni con Spring Boot', 80, '2024-06-01', '2024-08-15', 1200.00, 15, 'ATTIVO');

-- Inserimento aule di esempio
INSERT INTO aule (nome, capienza, descrizione) VALUES
('Aula 1', 30, 'Aula principale con proiettore'),
('Aula 2', 20, 'Aula per laboratori');

-- Inserimento lezioni di esempio
INSERT INTO lezioni (corso_id, aula_id, docente_id, data_ora_inizio, data_ora_fine, argomento, descrizione)
SELECT 
    c.id as corso_id,
    a.id as aula_id,
    u.id as docente_id,
    '2024-05-01 09:00:00' as data_ora_inizio,
    '2024-05-01 13:00:00' as data_ora_fine,
    'Introduzione a Java' as argomento,
    'Prima lezione del corso Java' as descrizione
FROM corsi c, aule a, utenti u
WHERE c.titolo = 'Java Avanzato'
AND a.nome = 'Aula 1'
AND u.username = 'docente1';

-- Inserimento iscrizioni di esempio
INSERT INTO iscrizioni (corso_id, studente_id, stato)
SELECT 
    c.id as corso_id,
    u.id as studente_id,
    'CONFERMATA' as stato
FROM corsi c, utenti u
WHERE c.titolo = 'Java Avanzato'
AND u.username = 'studente1'; 