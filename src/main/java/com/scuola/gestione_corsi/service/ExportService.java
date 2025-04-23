package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.model.*;
import com.scuola.gestione_corsi.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final StudenteRepository studenteRepository;
    private final DocenteRepository docenteRepository;
    private final IscrizioneRepository iscrizioneRepository;
    private final CorsoRepository corsoRepository;

    public byte[] exportStudentiToCsv() {
        List<Studente> studenti = studenteRepository.findAll();
        StringBuilder csv = new StringBuilder("Nome,Cognome,Data Nascita,Indirizzo,Telefono\n");
        
        for (Studente studente : studenti) {
            csv.append(studente.getNome()).append(",")
               .append(studente.getCognome()).append(",")
               .append(studente.getDataNascita()).append(",")
               .append(studente.getIndirizzo()).append(",")
               .append(studente.getTelefono()).append("\n");
        }
        
        return csv.toString().getBytes();
    }

    public byte[] exportDocentiToCsv() {
        List<Docente> docenti = docenteRepository.findAll();
        StringBuilder csv = new StringBuilder("Nome,Cognome,Specializzazione,CV,Tariffa\n");
        
        for (Docente docente : docenti) {
            csv.append(docente.getNome()).append(",")
               .append(docente.getCognome()).append(",")
               .append(docente.getSpecializzazione()).append(",")
               .append(docente.getCv()).append(",")
               .append(docente.getTariffa()).append("\n");
        }
        
        return csv.toString().getBytes();
    }

    public byte[] exportIscrizioniToCsv() {
        List<Iscrizione> iscrizioni = iscrizioneRepository.findAll();
        StringBuilder csv = new StringBuilder("Data Iscrizione,Stato,Metodo Pagamento,Studente,Corso\n");
        
        for (Iscrizione iscrizione : iscrizioni) {
            csv.append(iscrizione.getDataIscrizione()).append(",")
               .append(iscrizione.getStato()).append(",")
               .append(iscrizione.getMetodoPagamento()).append(",")
               .append(iscrizione.getStudente().getNome()).append(" ")
               .append(iscrizione.getStudente().getCognome()).append(",")
               .append(iscrizione.getCorso().getNome()).append("\n");
        }
        
        return csv.toString().getBytes();
    }

    public byte[] exportCorsiToExcel() {
        List<Corso> corsi = corsoRepository.findAll();
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Corsi");
            
            // Intestazioni
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Nome", "Descrizione", "Durata", "Max Studenti", "Prezzo", "Categoria"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // Dati
            int rowNum = 1;
            for (Corso corso : corsi) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(corso.getNome());
                row.createCell(1).setCellValue(corso.getDescrizione());
                row.createCell(2).setCellValue(corso.getDurata());
                row.createCell(3).setCellValue(corso.getMaxStudenti());
                row.createCell(4).setCellValue(corso.getPrezzo());
                row.createCell(5).setCellValue(corso.getCategoria().getNome());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'esportazione dei corsi in Excel", e);
        }
    }
} 