package com.scuola.gestione_corsi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.scuola.gestione_corsi.model.*;
import com.scuola.gestione_corsi.repository.StudenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final StudenteRepository studenteRepository;

    public byte[] generateProfiloStudente(Long studenteId) {
        Studente studente = studenteRepository.findById(studenteId)
                .orElseThrow(() -> new RuntimeException("Studente non trovato"));

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Titolo
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Profilo Studente", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Spazio

            // Informazioni personali
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("Informazioni Personali", sectionFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            addTableRow(table, "Nome:", studente.getNome());
            addTableRow(table, "Cognome:", studente.getCognome());
            addTableRow(table, "Data di Nascita:", studente.getDataNascita().toString());
            addTableRow(table, "Indirizzo:", studente.getIndirizzo());
            addTableRow(table, "Telefono:", studente.getTelefono());
            document.add(table);
            document.add(new Paragraph(" "));

            // Corsi e valutazioni
            document.add(new Paragraph("Corsi e Valutazioni", sectionFont));
            document.add(new Paragraph(" "));

            PdfPTable corsiTable = new PdfPTable(4);
            corsiTable.setWidthPercentage(100);
            Stream.of("Corso", "Data Iscrizione", "Stato", "Valutazione Media")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle));
                        corsiTable.addCell(header);
                    });

            studente.getIscrizioni().forEach(iscrizione -> {
                corsiTable.addCell(iscrizione.getCorso().getNome());
                corsiTable.addCell(iscrizione.getDataIscrizione().toString());
                corsiTable.addCell(iscrizione.getStato().toString());
                double mediaVoti = iscrizione.getValutazioni().stream()
                        .mapToInt(Valutazione::getVoto)
                        .average()
                        .orElse(0.0);
                corsiTable.addCell(String.format("%.2f", mediaVoti));
            });
            document.add(corsiTable);

            document.close();
            return out.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Errore durante la generazione del PDF", e);
        }
    }

    private void addTableRow(PdfPTable table, String label, String value) {
        table.addCell(new Phrase(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(value);
    }
} 