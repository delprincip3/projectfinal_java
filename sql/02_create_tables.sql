-- Creazione delle tabelle principali

-- Tabella Utenti
CREATE TABLE IF NOT EXISTS utenti (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    ruolo VARCHAR(20) NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Corsi
CREATE TABLE IF NOT EXISTS corsi (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    titolo VARCHAR(100) NOT NULL,
    descrizione TEXT,
    durata_ore INTEGER NOT NULL,
    data_inizio DATE NOT NULL,
    data_fine DATE NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    max_partecipanti INTEGER NOT NULL,
    stato VARCHAR(20) NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Aule
CREATE TABLE IF NOT EXISTS aule (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(50) NOT NULL,
    capienza INTEGER NOT NULL,
    descrizione TEXT,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Lezioni
CREATE TABLE IF NOT EXISTS lezioni (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    corso_id UUID REFERENCES corsi(id),
    aula_id UUID REFERENCES aule(id),
    docente_id UUID REFERENCES utenti(id),
    data_ora_inizio TIMESTAMP NOT NULL,
    data_ora_fine TIMESTAMP NOT NULL,
    argomento VARCHAR(200) NOT NULL,
    descrizione TEXT,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Iscrizioni
CREATE TABLE IF NOT EXISTS iscrizioni (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    corso_id UUID REFERENCES corsi(id),
    studente_id UUID REFERENCES utenti(id),
    data_iscrizione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    stato VARCHAR(20) NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(corso_id, studente_id)
);

-- Tabella Presenze
CREATE TABLE IF NOT EXISTS presenze (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    lezione_id UUID REFERENCES lezioni(id),
    studente_id UUID REFERENCES utenti(id),
    presente BOOLEAN NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(lezione_id, studente_id)
);

-- Tabella Materiali Didattici
CREATE TABLE IF NOT EXISTS materiali_didattici (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    corso_id UUID REFERENCES corsi(id),
    titolo VARCHAR(100) NOT NULL,
    descrizione TEXT,
    percorso_file VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Valutazioni
CREATE TABLE IF NOT EXISTS valutazioni (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    corso_id UUID REFERENCES corsi(id),
    studente_id UUID REFERENCES utenti(id),
    docente_id UUID REFERENCES utenti(id),
    voto DECIMAL(4,2) NOT NULL,
    commento TEXT,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Pagamenti
CREATE TABLE IF NOT EXISTS pagamenti (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    iscrizione_id UUID REFERENCES iscrizioni(id),
    importo DECIMAL(10,2) NOT NULL,
    data_pagamento TIMESTAMP NOT NULL,
    metodo_pagamento VARCHAR(50) NOT NULL,
    stato VARCHAR(20) NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
); 