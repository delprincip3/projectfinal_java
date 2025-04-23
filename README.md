# Sistema di Gestione Corsi Post-Diploma

## Obiettivo
Sviluppo di un sistema completo per la gestione di una scuola di corsi tecnici post-diploma, con funzionalità di amministrazione, gestione corsi, iscrizioni e reportistica.

## Requisiti di Sistema
- Java 21
- PostgreSQL 15+
- Maven 3.8+
- RAM minima: 4GB
- Spazio su disco: 2GB

## Tecnologie
- **Backend**: Spring Boot 3.x
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Sicurezza**: Spring Security con JWT
- **Documentazione API**: Swagger/OpenAPI
- **Librerie Utili**: 
  - Lombok per la riduzione del boilerplate code
  - Apache POI per la gestione dei file Excel
  - iText PDF per la generazione di documenti PDF

## Configurazione Iniziale

### 1. Configurazione Database
1. Installare PostgreSQL se non già installato
2. Creare un nuovo database:
   ```bash
   psql -U postgres -f sql/01_create_database.sql
   ```
3. Creare le tabelle:
   ```bash
   psql -U postgres -d gestione_corsi -f sql/02_create_tables.sql
   ```
4. Inserire dati di esempio (opzionale):
   ```bash
   psql -U postgres -d gestione_corsi -f sql/03_insert_example_data.sql
   ```

### 2. Configurazione Applicazione
1. Copiare il file di configurazione:
   ```bash
   cp configuration/application.properties.example src/main/resources/application.properties
   ```
2. Modificare le seguenti proprietà nel file `application.properties`:
   - `spring.datasource.password`: password del database
   - `jwt.secret`: chiave segreta per JWT (almeno 32 caratteri)
   - `spring.mail.username`: email per le notifiche
   - `spring.mail.password`: password dell'email

## Avvio del Progetto
1. Clonare il repository
2. Configurare il database come descritto sopra
3. Configurare l'applicazione come descritto sopra
4. Eseguire il comando:
   ```bash
   mvn spring-boot:run
   ```
5. Accedere all'interfaccia Swagger su: `http://localhost:8080/api/swagger-ui.html`

## Credenziali di Default
- **Admin**:
  - Username: admin
  - Password: admin123
- **Docente**:
  - Username: docente1
  - Password: password123
- **Studente**:
  - Username: studente1
  - Password: password123

## Query Utili
Il file `sql/04_queries_utili.sql` contiene query predefinite per:
- Lista studenti iscritti a un corso
- Calendario lezioni
- Presenze degli studenti
- Materiali didattici
- Valutazioni medie
- Stato pagamenti
- Aule disponibili
- Docenti e loro corsi

## Architettura
- Architettura RESTful basata su microservizi
- Pattern MVC (Model-View-Controller)
- JWT per l'autenticazione stateless
- Configurazione basata su properties

## Sicurezza e autenticazione
- Autenticazione basata su JWT (JSON Web Token)
- Ruoli utente implementati (ADMIN, DOCENTE, STUDENTE)
- Password crittografate con BCrypt
- Protezione CSRF
- Gestione sessioni stateless

## Funzionalità principali
- Gestione utenti e ruoli
- Gestione corsi e programmi
- Sistema di iscrizione
- Gestione presenze
- Generazione report in Excel e PDF

## Funzionalità aggiuntive
- API documentata con Swagger
- Logging configurabile
- Validazione input
- Gestione errori personalizzata

## Testing
- Test di integrazione con Spring Boot Test
- Test di sicurezza con Spring Security Test
- Test delle API con Swagger UI

## Design Pattern utilizzati
- Repository Pattern
- Service Layer Pattern
- DTO Pattern
- Builder Pattern (con Lombok)
- Filter Pattern (per JWT)
- Strategy Pattern (per la generazione report)

## Note finali
Il progetto è stato sviluppato seguendo le best practices di Spring Boot e Java. La documentazione completa delle API è disponibile tramite Swagger UI dopo l'avvio dell'applicazione.

## Risoluzione dei problemi comuni
1. **Errore di connessione al database**:
   - Verificare che PostgreSQL sia in esecuzione
   - Controllare le credenziali nel file `application.properties`
   - Verificare che il database `gestione_corsi` esista

2. **Errore di autenticazione**:
   - Verificare che l'utente esista nel database
   - Controllare che la password sia corretta
   - Verificare che il ruolo sia corretto

3. **Errore di avvio dell'applicazione**:
   - Verificare che tutte le dipendenze siano installate
   - Controllare che la porta 8080 sia disponibile
   - Verificare che Java 21 sia installato