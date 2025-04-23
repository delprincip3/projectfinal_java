-- Creazione del database
CREATE DATABASE gestione_corsi
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Connessione al database appena creato
\c gestione_corsi

-- Creazione delle estensioni necessarie
CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; 