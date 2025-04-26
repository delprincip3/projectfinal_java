package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.model.*;
import com.scuola.gestione_corsi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExportService {

    private final StudenteRepository studenteRepository;
    private final DocenteRepository docenteRepository;
    private final IscrizioneRepository iscrizioneRepository;
    private final CorsoRepository corsoRepository;
    private final ValutazioneRepository valutazioneRepository;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] exportStudentiToCsv() {
        log.debug("Esportazione studenti in CSV");
        List<Studente> studenti = studenteRepository.findAll();
        StringBuilder csv = new StringBuilder("ID,Nome,Cognome,Data Nascita,Indirizzo,Telefono,Email\n");
        
        for (Studente studente : studenti) {
            csv.append(studente.getId()).append(",")
               .append(escapeCSV(studente.getNome())).append(",")
               .append(escapeCSV(studente.getCognome())).append(",")
               .append(studente.getDataNascita().format(DATE_FORMATTER)).append(",")
               .append(escapeCSV(studente.getIndirizzo())).append(",")
               .append(escapeCSV(studente.getTelefono())).append(",")
               .append(escapeCSV(studente.getUtente().getEmail())).append("\n");
        }
        
        log.debug("Esportati {} studenti", studenti.size());
        return csv.toString().getBytes();
    }

    public byte[] exportDocentiToCsv() {
        log.debug("Esportazione docenti in CSV");
        List<Docente> docenti = docenteRepository.findAll();
        StringBuilder csv = new StringBuilder("ID,Nome,Cognome,Email,Specializzazione,Tariffa\n");
        
        for (Docente docente : docenti) {
            csv.append(docente.getId()).append(",")
               .append(escapeCSV(docente.getNome())).append(",")
               .append(escapeCSV(docente.getCognome())).append(",")
               .append(escapeCSV(docente.getUtente().getEmail())).append(",")
               .append(escapeCSV(docente.getSpecializzazione())).append(",")
               .append(docente.getTariffa()).append("\n");
        }
        
        log.debug("Esportati {} docenti", docenti.size());
        return csv.toString().getBytes();
    }

    public byte[] exportIscrizioniToCsv() {
        log.debug("Esportazione iscrizioni in CSV");
        // Ottieni tutte le iscrizioni
        List<Iscrizione> iscrizioni = iscrizioneRepository.findAll();
        StringBuilder csv = new StringBuilder("ID,Data Iscrizione,Stato,Metodo Pagamento,Studente ID,Studente Nome,Studente Cognome,Corso ID,Corso Nome,Num. Valutazioni,Media Valutazioni\n");
        
        for (Iscrizione iscrizione : iscrizioni) {
            // Ottieni i dati base dell'iscrizione
            String dataIscrizione = iscrizione.getDataIscrizione().format(DATE_TIME_FORMATTER);
            String stato = iscrizione.getStato().toString();
            String metodoPagamento = iscrizione.getMetodoPagamento();
            
            // Ottieni i dati dello studente
            Studente studente = iscrizione.getStudente();
            Long studenteId = studente.getId();
            String studenteNome = escapeCSV(studente.getNome());
            String studenteCognome = escapeCSV(studente.getCognome());
            
            // Ottieni i dati del corso
            Corso corso = iscrizione.getCorso();
            Long corsoId = corso.getId();
            String corsoNome = escapeCSV(corso.getNome());
            
            // Ottieni esplicitamente le valutazioni per questa iscrizione dal repository
            List<Valutazione> valutazioni = valutazioneRepository.findByIscrizione_Id(iscrizione.getId());
            int numValutazioni = valutazioni.size();
            
            // Calcola la media delle valutazioni
            String mediaValutazioni = "N/A";
            if (!valutazioni.isEmpty()) {
                double media = valutazioni.stream()
                    .mapToInt(Valutazione::getVoto)
                    .average()
                    .orElse(0.0);
                mediaValutazioni = String.format("%.2f", media);
            }
            
            // Crea la riga CSV
            csv.append(iscrizione.getId()).append(",")
               .append(dataIscrizione).append(",")
               .append(stato).append(",")
               .append(escapeCSV(metodoPagamento)).append(",")
               .append(studenteId).append(",")
               .append(studenteNome).append(",")
               .append(studenteCognome).append(",")
               .append(corsoId).append(",")
               .append(corsoNome).append(",")
               .append(numValutazioni).append(",")
               .append(mediaValutazioni).append("\n");
        }
        
        log.debug("Esportate {} iscrizioni", iscrizioni.size());
        return csv.toString().getBytes();
    }

    public byte[] exportCorsiToExcel() {
        log.debug("Esportazione corsi in Excel");
        List<Corso> corsi = corsoRepository.findAll();
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Corsi");
            
            // Stili
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            // Intestazioni
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Nome", "Descrizione", "Durata (ore)", "Max Studenti", "Prezzo (€)", "Categoria", "Docenti"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Dati
            int rowNum = 1;
            for (Corso corso : corsi) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(corso.getId());
                row.createCell(1).setCellValue(corso.getNome());
                row.createCell(2).setCellValue(corso.getDescrizione());
                row.createCell(3).setCellValue(corso.getDurata());
                row.createCell(4).setCellValue(corso.getMaxStudenti());
                row.createCell(5).setCellValue(corso.getPrezzo());
                row.createCell(6).setCellValue(corso.getCategoria().getNome());
                
                // Lista dei docenti
                StringBuilder docenti = new StringBuilder();
                if (corso.getDocenti() != null && !corso.getDocenti().isEmpty()) {
                    for (Docente docente : corso.getDocenti()) {
                        if (docenti.length() > 0) {
                            docenti.append(", ");
                        }
                        docenti.append(docente.getNome()).append(" ").append(docente.getCognome());
                    }
                }
                row.createCell(7).setCellValue(docenti.toString());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            log.debug("Esportati {} corsi in Excel", corsi.size());
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Errore durante l'esportazione dei corsi in Excel: {}", e.getMessage());
            throw new RuntimeException("Errore durante l'esportazione dei corsi in Excel", e);
        }
    }
    
    /**
     * Escapes special characters in CSV values
     */
    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        
        // If value contains comma, quote, or newline, wrap in quotes and escape quotes
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
} 