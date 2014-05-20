package BLL;

import BE.BEIncidentDetails;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLPDF {

    private static BLLPDF m_instance;
    private Image logo;
    
    private BLLPDF() {

    }

    public static BLLPDF getInstance() {
        if (m_instance == null) {
            m_instance = new BLLPDF();
        }
        return m_instance;
    }

    public void printToPDF(BEIncidentDetails incidentDetails) {

        Document document = new Document();
        String name = "PDFtest.pdf";
        try {
            FileOutputStream os = new FileOutputStream(name);
            PdfWriter.getInstance(document, os);
            
            document.open();
            try {
                logo = Image.getInstance("ebr.jpg");
            } catch (    BadElementException | IOException ex) {
                Logger.getLogger(BLLPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            logo.setAlignment(Element.ALIGN_RIGHT);
            logo.scalePercent(50);
            document.add(logo);
            Paragraph h = new Paragraph("15-06-2");
            h.setAlignment(Element.ALIGN_RIGHT);
            document.add(h);
            
            document.add(new Phrase(incidentDetails.getM_incident().getM_incidentName()));
            document.add(new Paragraph(incidentDetails.getM_incident().getM_date().toString()));
            document.add(new Paragraph("Something"));
            document.add(new Paragraph(incidentDetails.getM_remark()));
            document.close();
        
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(BLLPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }

}
