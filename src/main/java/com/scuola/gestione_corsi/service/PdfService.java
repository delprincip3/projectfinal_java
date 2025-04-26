package com.scuola.gestione_corsi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.scuola.gestione_corsi.model.*;
import com.scuola.gestione_corsi.repository.*;
import com.scuola.gestione_corsi.util.HeaderFooterPageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfService {

    private final StudenteRepository studenteRepository;
    private final ValutazioneRepository valutazioneRepository;
    private final IscrizioneRepository iscrizioneRepository;
    private static final Logger log = LoggerFactory.getLogger(PdfService.class);

    public byte[] generateProfiloStudente(Long studenteId) {
        log.debug("Generazione profilo PDF per studente con ID: {}", studenteId);
        Studente studente = studenteRepository.findById(studenteId)
                .orElseThrow(() -> new RuntimeException("Studente non trovato con ID: " + studenteId));

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new HeaderFooterPageEvent("Profilo Studente", "Scuola Corsi Tecnici"));
            document.open();

            // Aggiungo un watermark di confidenzialità
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                canvas.beginText();
                canvas.setColorFill(BaseColor.LIGHT_GRAY);
                canvas.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 60);
                canvas.showTextAligned(Element.ALIGN_CENTER, "CONFIDENZIALE", 
                    PageSize.A4.getWidth() / 2, 
                    PageSize.A4.getHeight() / 2, 
                    45);
                canvas.endText();
            } catch (Exception e) {
                log.warn("Impossibile aggiungere il watermark: {}", e.getMessage());
            }

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

            // Ottieni tutte le iscrizioni per questo studente (ricaricate dal repository per evitare problemi di lazy loading)
            List<Iscrizione> iscrizioni = iscrizioneRepository.findByStudente_Id(studenteId);
            log.debug("Trovate {} iscrizioni per lo studente", iscrizioni.size());
            
            for (Iscrizione iscrizione : iscrizioni) {
                corsiTable.addCell(iscrizione.getCorso().getNome());
                corsiTable.addCell(iscrizione.getDataIscrizione().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                corsiTable.addCell(iscrizione.getStato().toString());
                
                // Carica esplicitamente le valutazioni per questa iscrizione
                List<Valutazione> valutazioni = valutazioneRepository.findByIscrizione_Id(iscrizione.getId());
                log.debug("Trovate {} valutazioni per l'iscrizione {}", valutazioni.size(), iscrizione.getId());
                
                if (valutazioni.isEmpty()) {
                    corsiTable.addCell("N/A");
                } else {
                    double mediaVoti = valutazioni.stream()
                            .mapToInt(Valutazione::getVoto)
                            .average()
                            .orElse(0.0);
                    corsiTable.addCell(String.format("%.2f", mediaVoti));
                }
            }
            document.add(corsiTable);
            
            // Se non ci sono valutazioni, aggiungiamo un messaggio
            if (iscrizioni.isEmpty()) {
                Font noteFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC);
                document.add(new Paragraph("Lo studente non ha iscrizioni attive.", noteFont));
            }

            document.close();
            return out.toByteArray();
        } catch (DocumentException e) {
            log.error("Errore durante la generazione del PDF per lo studente {}: {}", studenteId, e.getMessage());
            throw new RuntimeException("Errore durante la generazione del PDF", e);
        } catch (Exception e) {
            log.error("Errore imprevisto durante la generazione del PDF per lo studente {}: {}", studenteId, e.getMessage());
            throw new RuntimeException("Errore imprevisto durante la generazione del PDF", e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private void addTableRow(PdfPTable table, String label, String value) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorderWidth(0.5f);
        labelCell.setPadding(5);
        labelCell.setBackgroundColor(new BaseColor(240, 240, 240));
        labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorderWidth(0.5f);
        valueCell.setPadding(5);
        valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
} 