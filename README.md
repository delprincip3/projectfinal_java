# Sistema di Gestione Corsi

## Obiettivo
Il Sistema di Gestione Corsi è un'applicazione web che permette la gestione completa di corsi formativi, studenti, docenti e tutte le attività correlate. L'obiettivo principale è fornire una piattaforma efficiente per la gestione di corsi didattici, iscrizioni, presenze, valutazioni e pagamenti.

## Tecnologie
- **Backend**: 
  - Java 17
  - Spring Boot 3.x
  - Spring Security con JWT
  - Spring Data JPA
  - Hibernate
  - PostgreSQL
  - Lombok
  - iTextPDF per la generazione di PDF
  - Apache POI per la gestione di file Excel
  - OpenCSV per la gestione di file CSV



## Architettura
L'applicazione segue un'architettura RESTful con:
- Controller Layer: Gestisce le richieste HTTP e le risposte
- Service Layer: Implementa la logica di business
- Repository Layer: Gestisce l'accesso ai dati
- Model Layer: Definisce le entità del dominio
- DTO Layer: Gestisce il trasferimento dei dati
- Mapper Layer: Converte tra entità e DTO

## Sicurezza e autenticazione
- Autenticazione basata su JWT (JSON Web Token)
- Ruoli implementati: ADMIN, DOCENTE, STUDENTE
- Endpoint protetti con autorizzazioni basate sui ruoli
- Password crittografate con BCrypt
- Validazione dei dati in input
- Protezione contro attacchi CSRF
- Timeout token JWT: 24 ore
- Gestione sessioni con refresh token
- Rate limiting per prevenire attacchi brute force

## Funzionalità principali
1. **Gestione Corsi**
   - Creazione, modifica, eliminazione corsi
   - Assegnazione docenti
   - Gestione categorie
   - Gestione aule

2. **Gestione Utenti**
   - Registrazione e login
   - Gestione profili
   - Gestione ruoli

3. **Gestione Iscrizioni**
   - Registrazione studenti ai corsi
   - Gestione stati iscrizione (ATTIVA, SOSPESA, ANNULLATA, COMPLETATA)
   - Monitoraggio pagamenti

4. **Gestione Lezioni**
   - Programmazione lezioni
   - Gestione presenze
   - Registrazione valutazioni

5. **Gestione Materiali**
   - Upload e download materiali didattici
   - Assegnazione materiali alle lezioni

## Funzionalità aggiuntive
1. **Export Dati**
   - Generazione PDF profili studenti
   - Export CSV di studenti, docenti e iscrizioni
   - Export Excel dei corsi

2. **Reportistica**
   - Statistiche presenze per corso e studente
   - Report valutazioni con medie e distribuzione
   - Monitoraggio pagamenti e stato finanziario
   - Query predefinite per analisi dati
   - Dashboard personalizzate per ruolo

## Testing
- Test unitari con JUnit (copertura > 80%)
- Test di integrazione con TestContainers
- Test delle API con Postman
- Test di sicurezza con OWASP ZAP
- Validazione dei dati
- Gestione errori
- Test di performance con JMeter

## Requisiti di Sistema
- Java 17 o superiore
- PostgreSQL 14 o superiore
- 4GB RAM minimo
- 2GB spazio su disco
- Connessione internet per dipendenze

## Avvio del progetto
1. Clonare il repository
2. Configurare il database PostgreSQL:
   - Creare database 'gestione_corsi'
   - Eseguire script SQL in ordine:
     - 01_create_database.sql
     - 02_create_tables.sql
     - 03_insert_example_data.sql
3. Modificare le configurazioni in `application.properties`:
   - Configurare URL database
   - Impostare credenziali
   - Configurare JWT secret
4. Eseguire il comando `mvn spring-boot:run`
5. Accedere all'applicazione su `http://localhost:8080`

## Dataset iniziale
Il sistema include un dataset iniziale con:
- Categorie di corsi predefinite
- Docenti di esempio
- Studenti di esempio
- Aule preconfigurate
- Lezioni di esempio
- Iscrizioni di esempio
- Ruoli utente standard

## Design Pattern utilizzati
- Repository Pattern
- Service Layer Pattern
- DTO Pattern
- Mapper Pattern
- Factory Pattern
- Strategy Pattern per l'export
- Builder Pattern per oggetti complessi
- Observer Pattern per eventi di sistema

## Note finali
- Il sistema è completamente documentato con JavaDoc
- Tutte le API sono documentate nel file `postman_requests.txt`
- Il codice segue le best practices di Spring Boot
- La sicurezza è implementata seguendo gli standard OWASP
- Il sistema è scalabile e mantenibile
- Documentazione API disponibile su Swagger UI
- Logging completo con SLF4J
- Monitoraggio con Spring Boot Actuator