# Sistema di Gestione Corsi Post-Diploma

## Obiettivo
Sviluppo di un sistema completo per la gestione di una scuola di corsi tecnici post-diploma, con funzionalità di amministrazione, gestione corsi, iscrizioni e reportistica.

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

## Avvio del progetto
1. Clonare il repository
2. Configurare PostgreSQL (creare database 'gestione_corsi')
3. Modificare le credenziali nel file `application.properties`
4. Eseguire il comando: `mvn spring-boot:run`
5. Accedere all'interfaccia Swagger su: `http://localhost:8080/swagger-ui.html`

## Dataset iniziale
Il database viene inizializzato automaticamente tramite JPA con `spring.jpa.hibernate.ddl-auto=update`

## Design Pattern utilizzati
- Repository Pattern
- Service Layer Pattern
- DTO Pattern
- Builder Pattern (con Lombok)
- Filter Pattern (per JWT)
- Strategy Pattern (per la generazione report)

## Note finali
Il progetto è stato sviluppato seguendo le best practices di Spring Boot e Java. La documentazione completa delle API è disponibile tramite Swagger UI dopo l'avvio dell'applicazione.