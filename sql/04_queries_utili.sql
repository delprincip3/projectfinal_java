-- Query utili per la gestione del sistema

-- 1. Lista studenti iscritti a un corso specifico
SELECT u.nome, u.cognome, u.email, i.data_iscrizione
FROM utenti u
JOIN iscrizioni i ON u.id = i.studente_id
JOIN corsi c ON i.corso_id = c.id
WHERE c.titolo = 'Java Avanzato'
ORDER BY u.cognome, u.nome;

-- 2. Calendario lezioni di un corso
SELECT l.data_ora_inizio, l.data_ora_fine, l.argomento, a.nome as aula, 
       CONCAT(u.nome, ' ', u.cognome) as docente
FROM lezioni l
JOIN corsi c ON l.corso_id = c.id
JOIN aule a ON l.aula_id = a.id
JOIN utenti u ON l.docente_id = u.id
WHERE c.titolo = 'Java Avanzato'
ORDER BY l.data_ora_inizio;

-- 3. Presenze degli studenti per una lezione
SELECT u.nome, u.cognome, p.presente
FROM presenze p
JOIN utenti u ON p.studente_id = u.id
JOIN lezioni l ON p.lezione_id = l.id
WHERE l.argomento = 'Introduzione a Java'
ORDER BY u.cognome, u.nome;

-- 4. Materiali didattici disponibili per un corso
SELECT md.titolo, md.descrizione, md.tipo, md.data_creazione
FROM materiali_didattici md
JOIN corsi c ON md.corso_id = c.id
WHERE c.titolo = 'Java Avanzato'
ORDER BY md.data_creazione DESC;

-- 5. Valutazioni medie degli studenti per corso
SELECT c.titolo, u.nome, u.cognome, 
       AVG(v.voto) as media_voti,
       COUNT(v.id) as numero_valutazioni
FROM valutazioni v
JOIN corsi c ON v.corso_id = c.id
JOIN utenti u ON v.studente_id = u.id
GROUP BY c.titolo, u.nome, u.cognome
ORDER BY c.titolo, media_voti DESC;

-- 6. Stato dei pagamenti per un corso
SELECT u.nome, u.cognome, p.importo, p.data_pagamento, p.stato
FROM pagamenti p
JOIN iscrizioni i ON p.iscrizione_id = i.id
JOIN utenti u ON i.studente_id = u.id
JOIN corsi c ON i.corso_id = c.id
WHERE c.titolo = 'Java Avanzato'
ORDER BY p.data_pagamento DESC;

-- 7. Aule disponibili in un determinato periodo
SELECT a.nome, a.capienza
FROM aule a
WHERE a.id NOT IN (
    SELECT l.aula_id
    FROM lezioni l
    WHERE l.data_ora_inizio BETWEEN '2024-05-01 09:00:00' AND '2024-05-01 13:00:00'
);

-- 8. Docenti e loro corsi
SELECT CONCAT(u.nome, ' ', u.cognome) as docente,
       COUNT(DISTINCT l.corso_id) as numero_corsi,
       GROUP_CONCAT(DISTINCT c.titolo) as corsi
FROM utenti u
JOIN lezioni l ON u.id = l.docente_id
JOIN corsi c ON l.corso_id = c.id
WHERE u.ruolo = 'DOCENTE'
GROUP BY u.id, u.nome, u.cognome; 