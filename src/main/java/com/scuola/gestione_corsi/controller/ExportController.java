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

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;
    private final PdfService pdfService;

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
    public ResponseEntity<byte[]> exportCorsiToExcel() {
        byte[] excel = exportService.exportCorsiToExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=corsi.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/studenti/{id}/pdf")
    public ResponseEntity<byte[]> exportStudentePdf(@PathVariable Long id) {
        byte[] pdf = pdfService.generateProfiloStudente(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=profilo_studente.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
} 