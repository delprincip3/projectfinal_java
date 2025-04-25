package com.scuola.gestione_corsi.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    private final String title;
    private final String companyName;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public HeaderFooterPageEvent(String title, String companyName) {
        this.title = title;
        this.companyName = companyName;
    }
    
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{1, 2});
            
            // Logo o nome dell'azienda
            try {
                Image logo = Image.getInstance(getClass().getResource("/static/images/logo.png"));
                logo.scaleToFit(70, 70);
                PdfPCell logoCell = new PdfPCell(logo);
                logoCell.setBorder(Rectangle.NO_BORDER);
                logoCell.setPaddingTop(5);
                header.addCell(logoCell);
            } catch (Exception e) {
                // Fallback al nome dell'azienda se l'immagine non è disponibile
                PdfPCell companyCell = new PdfPCell(new Phrase(companyName, 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
                companyCell.setBorder(Rectangle.NO_BORDER);
                header.addCell(companyCell);
            }
            
            // Informazioni del documento
            PdfPCell infoCell = new PdfPCell();
            infoCell.setBorder(Rectangle.NO_BORDER);
            infoCell.addElement(new Paragraph(title, 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            infoCell.addElement(new Paragraph("Generato il: " + 
                DATE_TIME_FORMATTER.format(LocalDateTime.now()),
                FontFactory.getFont(FontFactory.HELVETICA, 10)));
            header.addCell(infoCell);
            
            document.add(header);
        } catch (DocumentException e) {
            // Fallback silenzioso
        }
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable footer = new PdfPTable(3);
        try {
            footer.setWidths(new int[]{2, 1, 2});
            footer.setTotalWidth(PageSize.A4.getWidth() - document.leftMargin() - document.rightMargin());
            footer.setLockedWidth(true);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            
            // Colonna sinistra: nome azienda
            footer.addCell(new Phrase(companyName, 
                FontFactory.getFont(FontFactory.HELVETICA, 8)));
            
            // Colonna centrale: numero pagina
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(new Phrase(String.format("Pagina %d", writer.getPageNumber()), 
                FontFactory.getFont(FontFactory.HELVETICA, 8)));
            
            // Colonna destra: informazioni confidenzialità
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase("Documento confidenziale", 
                FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC)));
            
            footer.writeSelectedRows(0, -1, document.leftMargin(), 
                document.bottomMargin() - 10, writer.getDirectContent());
        } catch (Exception e) {
            // Ignora eventuali errori nel footer
        }
    }
} 