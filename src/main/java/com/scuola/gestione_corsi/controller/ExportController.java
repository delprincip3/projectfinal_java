package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.service.ExportService;
import com.scuola.gestione_corsi.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;
    private final PdfService pdfService;
    private static final Logger log = LoggerFactory.getLogger(ExportController.class);

    @GetMapping("/studenti/csv")
    public ResponseEntity<byte[]> exportStudentiToCsv() {
        byte[] csv = exportService.exportStudentiToCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=studenti.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping("/docenti/csv")
    public ResponseEntity<byte[]> exportDocentiToCsv() {
        byte[] csv = exportService.exportDocentiToCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=docenti.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping("/iscrizioni/csv")
    public ResponseEntity<byte[]> exportIscrizioniToCsv() {
        byte[] csv = exportService.exportIscrizioniToCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=iscrizioni.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping("/corsi/excel")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    @Operation(summary = "Esporta l'elenco dei corsi in formato Excel")
    public ResponseEntity<byte[]> exportCorsiToExcel() {
        log.debug("Ricevuta richiesta di esportazione corsi in Excel");
        byte[] excel = exportService.exportCorsiToExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=corsi.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/studenti/{id}/profilo")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    @Operation(summary = "Genera il PDF del profilo completo di uno studente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "PDF generato con successo"),
        @ApiResponse(responseCode = "404", description = "Studente non trovato"),
        @ApiResponse(responseCode = "403", description = "Accesso non autorizzato")
    })
    public ResponseEntity<byte[]> generateProfiloStudentePdf(@PathVariable Long id) {
        log.debug("Ricevuta richiesta di generazione PDF profilo per studente con ID: {}", id);
        byte[] pdf = pdfService.generateProfiloStudente(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=profilo_studente_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
} 