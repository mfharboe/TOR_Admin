package BLL;

import BE.BEIncidentDetails;
import BE.BERoleTime;
import BE.BEUsage;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BLLPDF {

    private static BLLPDF m_instance;
    private Image logo;
    
    private final String FILE_PATH = "";
    private final String FILE_TYPE = ".pdf";

    private BLLPDF() {

    }

    /**
     * 
     * @return m_instance of BLLPDF. 
     */
    public static BLLPDF getInstance() {
        if (m_instance == null) {
            m_instance = new BLLPDF();
        }
        return m_instance;
    }

    /**
     * Creates a new .pdf file with the given incidentDetails, the RoleTime and Usage for an Incident.
     * @param incidentDetails
     * @param roletime
     * @param usage 
     */
    public void printToPDF(BEIncidentDetails incidentDetails, ArrayList<BERoleTime> roletime, ArrayList<BEUsage> usage) {

        Document document = new Document();
        String name = incidentDetails.getM_incident().getM_date() + " - " + incidentDetails.getM_incident().getM_incidentName();
        try {
            FileOutputStream os = new FileOutputStream(new File(FILE_PATH + name + FILE_TYPE));
            PdfWriter.getInstance(document, os);

            document.open();

            Date date = new Date();
            int dayInt = date.getDate();
            String dayString = dayInt + "";
            if (dayInt < 10) {
                dayString = "0" + dayString;
            }
            int monthInt = date.getMonth() + 1;
            String monthString = monthInt + "";
            if (monthInt < 10) {
                monthString = "0" + monthString;
            }
            String myDate = (date.getYear() + 1900) + "-" + monthString + "-" + dayString;

            try {
                logo = Image.getInstance("ebr.jpg");
            } catch (BadElementException | IOException ex) {
                BLLError.getInstance().readLogoError();
            }

            Paragraph myLogoParagraph = new Paragraph();
            Phrase myDatePhrase = new Phrase("Udskrevet d. " + myDate);
            Paragraph myTypeAndDate = new Paragraph(incidentDetails.getM_incident().getM_incidentType().getM_description() + " d. " + incidentDetails.getM_incident().getM_date());
            Paragraph myTime = new Paragraph("Kl. " + incidentDetails.getM_incident().getM_time());
            Paragraph myEvaNr = new Paragraph("Eva nr: " + incidentDetails.getM_evaNumber());
            Paragraph myFireReportNr = new Paragraph("Rapport nr: " + incidentDetails.getM_fireReport());
            Paragraph myIncident = new Paragraph(incidentDetails.getM_incident().getM_incidentName());
            Paragraph myAlarmType = new Paragraph(incidentDetails.getM_alarm().getM_description());
            Paragraph myLeader = new Paragraph("Indsatsleder: " + incidentDetails.getM_incidentLeader());
            Paragraph myInvolved = new Paragraph("Skadeslidte:");
            Paragraph myInvolvedName = new Paragraph(incidentDetails.getM_involvedName());
            Paragraph myInvolvedAddress = new Paragraph(incidentDetails.getM_involvedAddress());
            Paragraph myDetector = new Paragraph("Evt. detektor nr: " + incidentDetails.getM_detectorNumber());
            Paragraph myGroup = new Paragraph("Evt. gruppe nr: " + incidentDetails.getM_groupNumber());
            Paragraph myRemarks = new Paragraph("Bemærkninger:");
            Paragraph myRemarkText = new Paragraph(incidentDetails.getM_remark());
            Paragraph newLine = new Paragraph(" ");

            PdfPTable roleTimeTable = new PdfPTable(2);

            PdfPCell roleTimeCell1 = new PdfPCell(new Paragraph("Fremmødte"));
            PdfPCell roleTimeCell2 = new PdfPCell(new Paragraph("Køretøj"));
            roleTimeCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            roleTimeCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            roleTimeTable.addCell(roleTimeCell1).setHorizontalAlignment(Element.ALIGN_CENTER);
            roleTimeTable.addCell(roleTimeCell2).setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPTable usageTable = new PdfPTable(2);
            PdfPCell usageCell1 = new PdfPCell(new Paragraph("Materiel"));
            PdfPCell usageCell2 = new PdfPCell(new Paragraph("Mængde"));
            usageCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            usageCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            usageTable.addCell(usageCell1).setHorizontalAlignment(Element.ALIGN_CENTER);
            usageTable.addCell(usageCell2).setHorizontalAlignment(Element.ALIGN_CENTER);

            for (BERoleTime beroletime : roletime) {
                if (!beroletime.isM_isOnStation()) {
                    roleTimeCell1 = new PdfPCell(new Paragraph(beroletime.getM_fireman().getM_firstName() + " " + beroletime.getM_fireman().getM_lastName()));
                    roleTimeCell2 = new PdfPCell(new Paragraph(beroletime.getM_vehicle().getM_odinNumber() + " - " + beroletime.getM_vehicle().getM_description()));
                    roleTimeTable.addCell(roleTimeCell1);
                    roleTimeTable.addCell(roleTimeCell2).setHorizontalAlignment(Element.ALIGN_CENTER);
                }
            }

            for (BERoleTime beroletime : roletime) {
                if (beroletime.isM_isOnStation()) {
                    roleTimeCell1 = new PdfPCell(new Paragraph(beroletime.getM_fireman().getM_firstName() + " " + beroletime.getM_fireman().getM_lastName()));
                    roleTimeCell2 = new PdfPCell(new Paragraph(beroletime.getM_role().getM_roleDescription()));
                    roleTimeTable.addCell(roleTimeCell1);
                    roleTimeTable.addCell(roleTimeCell2).setHorizontalAlignment(Element.ALIGN_CENTER);
                }
            }

            for (BEUsage beusage : usage) {
                usageCell1 = new PdfPCell(new Paragraph(beusage.getM_material().getM_description()));
                usageCell2 = new PdfPCell(new Paragraph(beusage.getM_amount() + ""));
                usageTable.addCell(usageCell1);
                usageTable.addCell(usageCell2).setHorizontalAlignment(Element.ALIGN_CENTER);
            }

            logo.setAlignment(Element.ALIGN_RIGHT);
            logo.scalePercent(50);
            myLogoParagraph.add(myDatePhrase);
            myLogoParagraph.add(logo);
            myTypeAndDate.setAlignment(Element.ALIGN_RIGHT);
            myEvaNr.setAlignment(Element.ALIGN_RIGHT);
            myFireReportNr.setAlignment(Element.ALIGN_RIGHT);
            myTime.setAlignment(Element.ALIGN_RIGHT);
            myIncident.setAlignment(Element.ALIGN_CENTER);
            myAlarmType.setAlignment(Element.ALIGN_CENTER);

            document.add(myLogoParagraph);
            document.add(myTypeAndDate);
            document.add(myTime);
            document.add(myEvaNr);
            document.add(myFireReportNr);
            document.add(myIncident);
            document.add(myAlarmType);
            document.add(newLine);
            document.add(myLeader);
            document.add(newLine);
            document.add(myInvolved);
            document.add(myInvolvedName);
            document.add(myInvolvedAddress);
            document.add(newLine);
            document.add(myDetector);
            document.add(myGroup);
            document.add(newLine);
            document.add(myRemarks);
            document.add(myRemarkText);
            document.add(newLine);
            document.add(newLine);
            document.add(roleTimeTable);
            document.add(newLine);
            document.add(usageTable);
            document.close();

        } catch (DocumentException | FileNotFoundException ex) {
            BLLError.getInstance().createPDFError();
        }

    }

}
